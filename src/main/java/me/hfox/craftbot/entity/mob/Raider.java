package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Monster;

public interface Raider extends Monster {

    boolean isCelebrating();

    void setCelebrating(boolean celebrating);

}
