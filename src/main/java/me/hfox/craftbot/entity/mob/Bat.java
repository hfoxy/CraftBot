package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.AmbientCreature;

public interface Bat extends AmbientCreature {

    boolean isHanging();

    void setHanging(boolean hanging);

}
