package me.hfox.craftbot.entity.living.mob;

import me.hfox.craftbot.entity.living.mob.Flying;

public interface Ghast extends Flying {

    boolean isAttacking();

    void setAttacking(boolean attacking);

}
