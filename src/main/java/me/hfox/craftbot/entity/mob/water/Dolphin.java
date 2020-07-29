package me.hfox.craftbot.entity.mob.water;

import me.hfox.craftbot.entity.mob.water.WaterAnimal;
import me.hfox.craftbot.world.Location;

public interface Dolphin extends WaterAnimal {

    Location getTreasurePosition();

    void setTreasurePosition(Location treasurePosition);

    boolean canFindTreasure();

    void setCanFindTreasure(boolean canFindTreasure);

    boolean hasFish();

    void setFish(boolean fish);

}
