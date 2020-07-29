package me.hfox.craftbot.nbt;

public class TagByte implements Tag {

    private final String name;
    private final byte value;

    public TagByte(String name, byte value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean hasName() {
        return name != null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TagType getType() {
        return TagType.BYTE;
    }

    public byte getValue() {
        return value;
    }

}
