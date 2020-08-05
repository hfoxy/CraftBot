package me.hfox.craftbot.entity.living.mob.animal.tameable;

import me.hfox.craftbot.entity.data.ParrotType;

public interface Parrot extends Tameable {

    ParrotType getType();

    void setType(ParrotType parrotType);

}
