package me.hfox.craftbot.entity.living.mob;

import me.hfox.craftbot.entity.living.mob.Golem;

public interface IronGolem extends Golem {

    boolean isPlayerCreated();

    void setPlayerCreated(boolean playerCreated);

}
