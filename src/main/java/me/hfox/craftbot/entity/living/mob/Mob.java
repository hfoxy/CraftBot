package me.hfox.craftbot.entity.living.mob;

import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.entity.living.LivingEntity;

public interface Mob extends LivingEntity {

    boolean hasAi();

    void setAi(boolean ai);

    Hand getMainHand();

    void setMainHand(Hand mainHand);

}
