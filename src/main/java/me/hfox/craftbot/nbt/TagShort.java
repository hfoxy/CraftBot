package me.hfox.craftbot.nbt;

public class TagShort implements Tag {

    private final String name;
    private final short value;

    public TagShort(String name, short value) {
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
        return TagType.SHORT;
    }

    public short getValue() {
        return value;
    }

}
