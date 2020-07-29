package me.hfox.craftbot.entity.living.mob.animal;

public interface Sheep extends Animal {

    int getColour();

    void setColour(int colour);

    boolean isSheared();

    void setSheared(boolean sheared);

}
