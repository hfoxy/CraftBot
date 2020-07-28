package me.hfox.craftbot.nbt;

public class TagEnd implements Tag, NamelessTag {

    @Override
    public TagType getType() {
        return TagType.END;
    }

}
