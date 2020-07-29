package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Monster;

public interface Blaze extends Monster {

    boolean isOnFire();

    void setOnFire(boolean onFire);

}
