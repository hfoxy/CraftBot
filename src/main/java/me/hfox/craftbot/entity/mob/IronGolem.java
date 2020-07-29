package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Golem;

public interface IronGolem extends Golem {

    boolean isPlayerCreated();

    void setPlayerCreated(boolean playerCreated);

}
