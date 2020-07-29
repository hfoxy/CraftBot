package me.hfox.craftbot.entity;

public interface Sheep extends Animal {

    int getColour();

    void setColour(int colour);

    boolean isSheared();

    void setSheared(boolean sheared);

}
