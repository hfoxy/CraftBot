package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.WaterAnimal;

public interface Fish extends WaterAnimal {

    boolean isFromBucket();

    void setFromBucket(boolean fromBucket);

}
