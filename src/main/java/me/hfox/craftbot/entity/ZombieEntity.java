package me.hfox.craftbot.entity;

public interface ZombieEntity extends MonsterEntity {

    boolean isBaby();

    void setBaby(boolean baby);

    boolean isBecomingDrowned();

    void setBecomingDrowned(boolean becomingDrowned);

}
