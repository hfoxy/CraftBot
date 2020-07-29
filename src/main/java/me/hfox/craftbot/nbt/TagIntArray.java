package me.hfox.craftbot.nbt;

public class TagIntArray implements Tag {

    private final String name;
    private final int[] value;

    public TagIntArray(String name, int[] value) {
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
        return TagType.INT_ARRAY;
    }

    public int[] getValue() {
        return value;
    }

}
