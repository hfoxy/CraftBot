package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Monster;

public interface Vex extends Monster {

    boolean isAttackMode();

    void setAttackMode(boolean attackMode);

}
