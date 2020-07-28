package me.hfox.craftbot.nbt;

public class TagCompound implements Tag {

    private final String name;
    private final Tag[] tags;

    public TagCompound(String name, Tag[] tags) {
        this.name = name;
        this.tags = tags;
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
        return TagType.COMPOUND;
    }

    public Tag[] getTags() {
        return tags;
    }

}
