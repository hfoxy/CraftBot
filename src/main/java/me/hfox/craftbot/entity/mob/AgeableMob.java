package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.PathfinderMob;

public interface AgeableMob extends PathfinderMob {

    boolean isBaby();

    void setBaby(boolean baby);

}
