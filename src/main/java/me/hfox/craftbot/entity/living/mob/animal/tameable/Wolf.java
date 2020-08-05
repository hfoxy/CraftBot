package me.hfox.craftbot.entity.living.mob.animal.tameable;

public interface Wolf extends Tameable {

    boolean isBegging();

    void setBegging(boolean begging);

    int getCollarColour();

    void setCollarColour(int collarColour);

}
