package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Flying;

public interface Ghast extends Flying {

    boolean isAttacking();

    void setAttacking(boolean attacking);

}
