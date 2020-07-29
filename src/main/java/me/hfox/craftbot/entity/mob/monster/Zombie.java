package me.hfox.craftbot.entity.mob.monster;

import me.hfox.craftbot.entity.mob.monster.Monster;

public interface Zombie extends Monster {

    boolean isBaby();

    void setBaby(boolean baby);

    boolean isBecomingDrowned();

    void setBecomingDrowned(boolean becomingDrowned);

}
