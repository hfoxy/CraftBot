package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadataType;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketServerPlayEntityMetadata implements ServerPacket {

    private int entityId;
    private List<EntityMetadata> metadata;

    public int getEntityId() {
        return entityId;
    }

    public List<EntityMetadata> getMetadata() {
        return new ArrayList<>(metadata);
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        metadata = new ArrayList<>();

        while (true) {
            short index = buffer.readUnsignedByte();
            if (index == 0xFF) {
                return;
            }

            int typeId = buffer.readVarInt();
            EntityMetadataType type = EntityMetadataType.findById(typeId);

            int startIndex = buffer.readerIndex();
            buffer.markReaderIndex();
            type.read(buffer);
            int endIndex = buffer.readerIndex();
            buffer.resetReaderIndex();

            int byteCount = endIndex - startIndex;
            byte[] data = new byte[byteCount];
            buffer.readBytes(data);

            metadata.add(new EntityMetadata(index, type, data));
        }
    }

}
