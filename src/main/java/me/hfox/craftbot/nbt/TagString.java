package me.hfox.craftbot.nbt;

public class TagString implements Tag {

    private final String name;
    private final String value;

    public TagString(String name, String value) {
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
        return TagType.STRING;
    }

    public String getValue() {
        return value;
    }

}
