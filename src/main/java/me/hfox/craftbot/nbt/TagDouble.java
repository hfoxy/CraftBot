package me.hfox.craftbot.nbt;

public class TagDouble implements Tag {

    private final String name;
    private final double value;

    public TagDouble(String name, double value) {
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
        return TagType.DOUBLE;
    }

    public double getValue() {
        return value;
    }

}
