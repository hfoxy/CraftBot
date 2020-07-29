package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.entity.LivingEntity;

public interface Mob extends LivingEntity {

    boolean hasAi();

    void setAi(boolean ai);

    Hand getMainHand();

    void setMainHand(Hand mainHand);

}
