package me.hfox.craftbot.entity.living.mob;

public interface AgeableMob extends PathfinderMob {

    boolean isBaby();

    void setBaby(boolean baby);

}
