package me.hfox.craftbot.entity.mob.animal;

import me.hfox.craftbot.entity.mob.animal.Tameable;

public interface Wolf extends Tameable {

    boolean isBegging();

    void setBegging(boolean begging);

    int getCollarColour();

    void setCollarColour(int collarColour);

}
