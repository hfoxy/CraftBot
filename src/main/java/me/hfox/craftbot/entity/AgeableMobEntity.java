package me.hfox.craftbot.entity;

public interface AgeableMobEntity extends PathfinderMobEntity {

    boolean isBaby();

    void setBaby(boolean baby);

}
