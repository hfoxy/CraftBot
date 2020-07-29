package me.hfox.craftbot.entity.mob.animal;

import me.hfox.craftbot.entity.mob.animal.Animal;

public interface Sheep extends Animal {

    int getColour();

    void setColour(int colour);

    boolean isSheared();

    void setSheared(boolean sheared);

}
