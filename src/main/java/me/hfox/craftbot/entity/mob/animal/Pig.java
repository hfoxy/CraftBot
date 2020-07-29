package me.hfox.craftbot.entity.mob.animal;

import me.hfox.craftbot.entity.mob.animal.Animal;

public interface Pig extends Animal {

    boolean hasSaddle();

    void setSaddle(boolean saddle);

    int getBoostTime();

    void setBoostTime(int boostTime);

}
