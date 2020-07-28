package me.hfox.craftbot.entity;

public interface SheepEntity extends AnimalEntity {

    int getColour();

    void setColour(int colour);

    boolean isSheared();

    void setSheared(boolean sheared);

}
