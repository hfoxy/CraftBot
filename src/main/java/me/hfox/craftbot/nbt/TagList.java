package me.hfox.craftbot.nbt;

public class TagList implements Tag {

    private final String name;
    private final TagType valueType;
    private final Tag[] value;

    public TagList(String name, TagType valueType, Tag[] value) {
        this.name = name;
        this.valueType = valueType;
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
        return TagType.LIST;
    }

    public TagType getValueType() {
        return valueType;
    }

    public Tag[] getValue() {
        return value;
    }

}
