package me.hfox.craftbot.protocol.stream;

import io.netty.buffer.ByteBuf;
import me.hfox.craftbot.Bot;
import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.chat.StringSupportedChatComponent;
import me.hfox.craftbot.nbt.*;
import me.hfox.craftbot.protocol.play.server.data.player.PlayerInfoProperty;
import me.hfox.craftbot.protocol.play.server.data.recipe.IngredientData;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
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

    public SlotData readSlot() throws IOException {
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

    public TagSet readTags() throws IOException {
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

    public Tag readTag() throws IOException {
        return readTag(false);
    }

    public Tag readTag(boolean list) throws IOException {
        // return new NBTInputStream(new ByteBufInputStream(this)).readTag(512).getTag();
        byte typeId = readByte();
        TagType type = TagType.values()[typeId];
        LOGGER.debug("Read typeId as {} ({})", type, typeId);

        String name;
        switch (type) {
            case END:
                return new TagEnd();
            case BYTE:
                break;
            case SHORT:
                break;
            case INT:
                name = readTagName(list);
                return new TagInt(name, readInt());
            case LONG:
                break;
            case FLOAT:
                break;
            case DOUBLE:
                break;
            case BYTE_ARRAY:
                break;
            case STRING:
                break;
            case LIST:
                break;
            case COMPOUND:
                name = readTagName(list);

                List<Tag> tagList = new ArrayList<>();

                while (true) {
                    Tag read = readTag(false);
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
                break;
            case LONG_ARRAY:
                break;
        }

        throw new IOException("nope");
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

    public ChatComponent readChat() throws IOException {
        return Bot.getBot().getMapper().readValue(readString(), StringSupportedChatComponent.class);
    }

    public void writeChat(ChatComponent chat) {
        throw new UnsupportedOperationException("not done yet");
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

}
