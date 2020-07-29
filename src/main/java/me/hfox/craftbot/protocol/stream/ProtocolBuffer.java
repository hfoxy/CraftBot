package me.hfox.craftbot.protocol.stream;

import io.netty.buffer.ByteBuf;
import me.hfox.craftbot.Bot;
import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.chat.StringSupportedChatComponent;
import me.hfox.craftbot.entity.data.Pose;
import me.hfox.craftbot.entity.data.VillagerData;
import me.hfox.craftbot.entity.data.VillagerProfession;
import me.hfox.craftbot.entity.data.VillagerType;
import me.hfox.craftbot.entity.particle.Particle;
import me.hfox.craftbot.exception.nbt.BotUnknownTagTypeException;
import me.hfox.craftbot.nbt.*;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.play.server.data.player.PlayerInfoProperty;
import me.hfox.craftbot.protocol.play.server.data.recipe.IngredientData;
import me.hfox.craftbot.world.Direction;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.Rotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * Buffer implementation, uses the buffer from the constructor to read/write
 *
 */
public class ProtocolBuffer extends NestedBuffer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolBuffer.class);

    public ProtocolBuffer(ByteBuf buffer) {
        super(buffer);
    }

    public int getVarIntLength(int var) {
        for (int j = 1; j < 5; ++j) {
            if ((var & -1 << j * 7) == 0) {
                return j;
            }
        }

        return 5;
    }

    public int readVarInt() {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public void writeVarInt(int value) {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }

            writeByte(temp);
        } while (value != 0);
    }

    public Optional<Integer> readVarIntOptional() {
        int value = readVarInt();
        if (value == 0) {
            return Optional.empty();
        }

        return Optional.of(value - 1);
    }

    public long readVarLong() {
        int numRead = 0;
        long result = 0;
        byte read;
        do {
            read = readByte();
            long value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 10) {
                throw new RuntimeException("VarLong is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public void writeVarLong(long value) {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }

            writeByte(temp);
        } while (value != 0);
    }

    public String readString(int length) {
        byte[] content = new byte[length];
        readBytes(content);
        return new String(content, StandardCharsets.UTF_8);
    }

    public String readString() {
        return readString(readVarInt());
    }

    public void writeString(String string) {
        byte[] content = string.getBytes();
        writeVarInt(content.length);
        writeBytes(content);
    }

    public void writeUnsignedShort(int value) {
        short v = (short) (value & 0xFF);
        writeShort(v);
    }

    public IngredientData readIngredient() throws IOException {
        int count = readVarInt();
        SlotData[] items = new SlotData[count];
        for (int i = 0; i < items.length; i++) {
            items[i] = readSlot();
        }

        return new IngredientData(items);
    }

    public SlotData readSlot() {
        boolean present = readBoolean();

        if (present) {
            Integer itemId = readVarInt();
            Byte count = readByte();
            Tag nbt = readTag();
            LOGGER.debug("Read slot as {}x{}: {}", itemId, count, nbt);
            return new SlotData(itemId, count, nbt);
        }

        return new SlotData();
    }

    public TagSet readTags() {
        List<Tag> tags = new ArrayList<>();
        while (true) {
            Tag read = readTag();
            tags.add(read);

            if (read instanceof TagEnd) {
                break;
            }
        }

        return new TagSet(tags);
    }

    public Tag readTag() {
        byte typeId = readByte();
        TagType type = TagType.values()[typeId];
        LOGGER.debug("Read typeId as {} ({})", type, typeId);
        return readTag(type);
    }

    public Tag readTag(TagType type) {
        return readTag(type, false);
    }

    public Tag readTag(TagType type, boolean list) {
        String name;
        switch (type) {
            case END:
                return new TagEnd();
            case BYTE:
                name = readTagName(list);
                return new TagByte(name, readByte());
            case SHORT:
                name = readTagName(list);
                return new TagShort(name, readShort());
            case INT:
                name = readTagName(list);
                return new TagInt(name, readInt());
            case LONG:
                name = readTagName(list);
                return new TagLong(name, readLong());
            case FLOAT:
                name = readTagName(list);
                return new TagFloat(name, readFloat());
            case DOUBLE:
                name = readTagName(list);
                return new TagDouble(name, readDouble());
            case BYTE_ARRAY:
                name = readTagName(list);
                byte[] byteArray = new byte[readInt()];
                readBytes(byteArray);
                return new TagByteArray(name, byteArray);
            case STRING:
                name = readTagName(list);
                return new TagString(name, readString(readShort()));
            case LIST:
                name = readTagName(list);
                int listTypeId = readByte();
                TagType listType = TagType.values()[listTypeId];

                Tag[] listContent = new Tag[readInt()];
                for (int i = 0; i < listContent.length; i++) {
                    listContent[i] = readTag(listType, true);
                }

                return new TagList(name, listType, listContent);
            case COMPOUND:
                name = readTagName(list);

                List<Tag> tagList = new ArrayList<>();

                while (true) {
                    Tag read = readTag();
                    tagList.add(read);
                    LOGGER.debug("Added {} ('{}') to compound ('{}')", read, read.getName(), name);

                    if (read instanceof TagEnd) {
                        break;
                    }
                }

                int index = 0;
                Tag[] tags = new Tag[tagList.size()];
                for (Tag tag : tagList) {
                    tags[index++] = tag;
                }

                return new TagCompound(name, tags);
            case INT_ARRAY:
                name = readTagName(list);
                int[] intArray = new int[readInt()];
                for (int i = 0; i < intArray.length; i++) {
                    intArray[i] = readInt();
                }

                return new TagIntArray(name, intArray);
            case LONG_ARRAY:
                name = readTagName(list);
                long[] longArray = new long[readInt()];
                for (int i = 0; i < longArray.length; i++) {
                    longArray[i] = readLong();
                }

                return new TagLongArray(name, longArray);
        }

        throw new BotUnknownTagTypeException(type.name());
    }

    private String readTagName(boolean list) {
        if (!list) {
            return readString(readShort());
        }

        return null;
    }

    public void writeTag(Tag tag) throws IOException {
        throw new UnsupportedOperationException("Not done yet");
        // new NBTSerializer(false).toStream(tag, new ByteBufOutputStream(this));
    }

    public UUID readUuid() {
        return new UUID(readLong(), readLong());
    }

    public void writeUuid(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public Optional<UUID> readUuidOptional() {
        if (readBoolean()) {
            return Optional.of(readUuid());
        }

        return Optional.empty();
    }

    public ChatComponent readChat() throws IOException {
        return Bot.getBot().getMapper().readValue(readString(), StringSupportedChatComponent.class);
    }

    public void writeChat(ChatComponent chat) {
        throw new UnsupportedOperationException("not done yet");
    }

    public Optional<ChatComponent> readChatOptional() throws IOException {
        if (readBoolean()) {
            return Optional.of(readChat());
        }

        return Optional.empty();
    }

    public PlayerInfoProperty readPlayerInfoProperty() throws IOException {
        String name = readString();
        String value = readString();
        String signature = readBoolean() ? readString() : null;
        return new PlayerInfoProperty(name, value, signature);
    }

    public void writePlayerInfoProperty(PlayerInfoProperty property) {
        writeString(property.getName());
        writeString(property.getValue());
        writeBoolean(property.getSignature().isPresent());
        property.getSignature().ifPresent(this::writeString);
    }

    public float readAngle() {
        return (readByte() / -128F) * 180;
    }

    public void writeAngle(float value) {
        byte b = (byte) ((value / 180) * -128);
        writeByte(b);
    }

    public int readBlockId() {
        return readVarInt();
    }

    public Optional<Integer> readBlockIdOptional() {
        int value = readVarInt();
        if (value == 0) {
            return Optional.empty();
        }

        return Optional.of(value);
    }

    public Rotation readRotation() {
        return new Rotation(readFloat(), readFloat(), readFloat());
    }

    public Location readPosition() {
        long value = readLong();
        int x = (int) (value >> 38);
        int y = (int) (value & 0xFFF);
        int z = (int) (value << 26 >> 38);

        if (x >= Math.pow(2, 25)) {
            x -= Math.pow(2, 26);
        }

        if (y >= Math.pow(2, 11)) {
            y -= Math.pow(2, 12);
        }

        if (z >= Math.pow(2, 25)) {
            z -= Math.pow(2, 26);
        }

        return new Location(x, y, z);
    }

    public Optional<Location> readPositionOptional() {
        if (readBoolean()) {
            return Optional.of(readPosition());
        }

        return Optional.empty();
    }

    public Direction readDirection() {
        return Direction.findById(readVarInt());
    }

    public Particle readParticle() {
        throw new UnsupportedOperationException("not done yet");
    }

    public VillagerData readVillagerData() {
        return new VillagerData(
                VillagerType.findById(readVarInt()),
                VillagerProfession.findById(readVarInt()),
                readVarInt()
        );
    }

    public Pose readPose() {
        return Pose.values()[readVarInt()];
    }

    public byte[] readByteArray() {
        byte[] data = new byte[readVarInt()];
        readBytes(data);
        return data;
    }

}
