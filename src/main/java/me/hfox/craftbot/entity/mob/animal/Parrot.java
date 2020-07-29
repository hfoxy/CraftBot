package me.hfox.craftbot.entity.mob.animal;

import me.hfox.craftbot.entity.data.ParrotType;

public interface Parrot extends Tameable {

    ParrotType getParrotType();

    void setParrotType(ParrotType parrotType);

}
