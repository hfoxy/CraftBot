package me.hfox.craftbot.entity.living.mob.animal;

import me.hfox.craftbot.entity.data.MooshroomType;

public interface Mooshroom extends Animal {

    MooshroomType getType();

    void setType(MooshroomType type);

}
