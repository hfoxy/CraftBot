package me.hfox.craftbot.protocol.play.server.data.entity;

public class EntityMetadataBytePart implements EntityMetadataPart {

    private final byte value;

    public EntityMetadataBytePart(byte value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return null;
    }

}
