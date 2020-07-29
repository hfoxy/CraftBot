package me.hfox.craftbot.entity.mob.water;

import me.hfox.craftbot.entity.mob.water.WaterAnimal;

public interface Fish extends WaterAnimal {

    boolean isFromBucket();

    void setFromBucket(boolean fromBucket);

}
