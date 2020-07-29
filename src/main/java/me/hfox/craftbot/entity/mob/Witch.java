package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Raider;

public interface Witch extends Raider {

    boolean isDrinkingPotion();

    void setDrinkingPotion(boolean drinkingPotion);

}
