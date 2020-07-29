package me.hfox.craftbot.entity;

public interface AgeableMob extends PathfinderMob {

    boolean isBaby();

    void setBaby(boolean baby);

}
