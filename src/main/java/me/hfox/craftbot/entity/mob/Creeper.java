package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Monster;

public interface Creeper extends Monster {

    boolean isFused();

    void setFused(boolean fused);

    boolean isCharged();

    void setCharged(boolean charged);

    boolean isIgnited();

    void setIgnited(boolean ignited);

}
