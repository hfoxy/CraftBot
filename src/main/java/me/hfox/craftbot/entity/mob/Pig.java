package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Animal;

public interface Pig extends Animal {

    boolean hasSaddle();

    void setSaddle(boolean saddle);

    int getBoostTime();

    void setBoostTime(int boostTime);

}
