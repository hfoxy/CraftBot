package me.hfox.craftbot.nbt;

public class TagLongArray implements Tag {

    private final String name;
    private final long[] value;

    public TagLongArray(String name, long[] value) {
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
        return TagType.LONG_ARRAY;
    }

    public long[] getValue() {
        return value;
    }

}
