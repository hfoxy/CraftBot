package me.hfox.craftbot.entity.living.mob.animal;

public interface Wolf extends Tameable {

    boolean isBegging();

    void setBegging(boolean begging);

    int getCollarColour();

    void setCollarColour(int collarColour);

}