package me.hfox.craftbot.nbt;

public class TagFloat implements Tag {

    private final String name;
    private final float value;

    public TagFloat(String name, float value) {
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
        return TagType.FLOAT;
    }

    public float getValue() {
        return value;
    }

}
