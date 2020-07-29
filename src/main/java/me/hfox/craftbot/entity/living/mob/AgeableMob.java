package me.hfox.craftbot.entity.living.mob;

import me.hfox.craftbot.entity.living.mob.PathfinderMob;

public interface AgeableMob extends PathfinderMob {

    boolean isBaby();

    void setBaby(boolean baby);

}
