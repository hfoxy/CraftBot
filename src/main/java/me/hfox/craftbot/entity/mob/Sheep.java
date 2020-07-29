package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Animal;

public interface Sheep extends Animal {

    int getColour();

    void setColour(int colour);

    boolean isSheared();

    void setSheared(boolean sheared);

}
