package me.hfox.craftbot.entity.living.mob.monster;

public interface Zombie extends Monster {

    boolean isBaby();

    void setBaby(boolean baby);

    boolean isBecomingDrowned();

    void setBecomingDrowned(boolean becomingDrowned);

}
