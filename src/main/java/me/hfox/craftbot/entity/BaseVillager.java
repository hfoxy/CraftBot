package me.hfox.craftbot.entity;

import me.hfox.craftbot.entity.mob.AgeableMob;

public interface BaseVillager extends AgeableMob {

    int getHeadShakeTimer();

    void setHeadShakeTimer(int headShakeTimer);

}
