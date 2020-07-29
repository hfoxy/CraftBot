package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Monster;

public interface Spider extends Monster {

    boolean isClimbing();

    void setClimbing(boolean climbing);

}
