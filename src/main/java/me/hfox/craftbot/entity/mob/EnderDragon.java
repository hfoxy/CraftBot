package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.data.DragonPhase;

public interface EnderDragon extends Mob {

    DragonPhase getDragonPhase();

    void setDragonPhase(DragonPhase dragonPhase);

}
