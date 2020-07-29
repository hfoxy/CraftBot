package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Tameable;

public interface Wolf extends Tameable {

    boolean isBegging();

    void setBegging(boolean begging);

    int getCollarColour();

    void setCollarColour(int collarColour);

}
