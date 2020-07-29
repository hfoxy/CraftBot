package me.hfox.craftbot.entity.living.mob.animal;

public interface Pig extends Animal {

    boolean hasSaddle();

    void setSaddle(boolean saddle);

    int getBoostTime();

    void setBoostTime(int boostTime);

}
