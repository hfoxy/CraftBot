package me.hfox.craftbot.nbt;

import java.util.List;

public class TagSet implements Tag, NamelessTag {

    private final List<Tag> tags;

    public TagSet(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public TagType getType() {
        return TagType.COMPOUND;
    }

    public List<Tag> getTags() {
        return tags;
    }

}
