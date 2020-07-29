package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.MooshroomType;
import me.hfox.craftbot.entity.mob.Animal;

public interface Mooshroom extends Animal {

    MooshroomType getType();

    void setType(MooshroomType type);

}
