package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Monster;

public interface Piglin extends Monster {

    boolean isImmuneToZombification();

    void setImmuneToZombification(boolean immuneToZombification);

    boolean isBaby();

    void setBaby(boolean baby);

    boolean isChargingCrossbow();

    void setChargingCrossbow(boolean chargingCrossbow);

    boolean isDancing();

    void setDancing(boolean dancing);

}
