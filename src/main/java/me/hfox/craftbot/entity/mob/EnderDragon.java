package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.DragonPhase;
import me.hfox.craftbot.entity.mob.Mob;

public interface EnderDragon extends Mob {

    DragonPhase getDragonPhase();

    void setDragonPhase(DragonPhase dragonPhase);

}
