package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Monster;

public interface Zombie extends Monster {

    boolean isBaby();

    void setBaby(boolean baby);

    boolean isBecomingDrowned();

    void setBecomingDrowned(boolean becomingDrowned);

}
