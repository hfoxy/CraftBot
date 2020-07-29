package me.hfox.craftbot.entity;

public interface Zombie extends Monster {

    boolean isBaby();

    void setBaby(boolean baby);

    boolean isBecomingDrowned();

    void setBecomingDrowned(boolean becomingDrowned);

}
