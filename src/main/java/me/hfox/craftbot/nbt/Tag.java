package me.hfox.craftbot.nbt;

public interface Tag {

    TagType getType();

    boolean hasName();

    String getName();

}
