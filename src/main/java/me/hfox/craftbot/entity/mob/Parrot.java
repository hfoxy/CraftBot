package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.ParrotType;
import me.hfox.craftbot.entity.mob.Tameable;

public interface Parrot extends Tameable {

    ParrotType getParrotType();

    void setParrotType(ParrotType parrotType);

}
