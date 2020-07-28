package me.hfox.craftbot.nbt;

public interface NamelessTag extends Tag {

    @Override
    default boolean hasName() {
        return false;
    }

    @Override
    default String getName() {
        return null;
    }

}
