package me.hfox.craftbot.protocol.play.server.data.entity;

import com.google.common.base.Preconditions;
import io.netty.buffer.Unpooled;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class EntityMetadata {

    private final int index;
    private final EntityMetadataType type;
    private final byte[] value;

    public EntityMetadata(int index, EntityMetadataType type, byte[] value) {
        Preconditions.checkNotNull(value, "metadata value cannot be null");

        this.index = index;
        this.type = type;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public EntityMetadataType getType() {
        return type;
    }

    public byte[] getValue() {
        return value;
    }

    public ProtocolBuffer getBufferValue() {
        return new ProtocolBuffer(Unpooled.copiedBuffer(value));
    }

}
