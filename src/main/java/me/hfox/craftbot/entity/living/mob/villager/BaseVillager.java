package me.hfox.craftbot.entity.living.mob.villager;

import me.hfox.craftbot.entity.living.mob.AgeableMob;

public interface BaseVillager extends AgeableMob {

    int getHeadShakeTimer();

    void setHeadShakeTimer(int headShakeTimer);

}
