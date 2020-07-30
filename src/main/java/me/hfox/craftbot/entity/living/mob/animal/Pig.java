package me.hfox.craftbot.entity.living.mob.animal;

public interface Pig extends Animal {

    boolean isSaddled();

    void setSaddled(boolean saddled);

    int getBoostTime();

    void setBoostTime(int boostTime);

}
