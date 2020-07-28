package me.hfox.craftbot.entity;

public interface WolfEntity extends TameableEntity {

    boolean isBegging();

    void setBegging(boolean begging);

    int getCollarColour();

    void setCollarColour(int collarColour);

}
