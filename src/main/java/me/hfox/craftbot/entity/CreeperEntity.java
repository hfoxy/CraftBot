package me.hfox.craftbot.entity;

public interface CreeperEntity extends MonsterEntity {

    boolean isFused();

    void setFused(boolean fused);

    boolean isCharged();

    void setCharged(boolean charged);

    boolean isIgnited();

    void setIgnited(boolean ignited);

}
