package me.hfox.craftbot.entity;

public interface PigEntity extends AnimalEntity {

    boolean hasSaddle();

    void setSaddle(boolean saddle);

    int getBoostTime();

    void setBoostTime(int boostTime);

}
