package me.hfox.craftbot.entity.mob.monster;

import me.hfox.craftbot.entity.mob.monster.Monster;

public interface Vex extends Monster {

    boolean isAttackMode();

    void setAttackMode(boolean attackMode);

}
