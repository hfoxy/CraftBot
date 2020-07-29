package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.CatType;
import me.hfox.craftbot.entity.mob.Tameable;

public interface Cat extends Tameable {

    CatType getType();

    void setCatType(CatType catType);

    boolean getUnknownBooleanA();

    void setUnknownBooleanA(boolean unknownBooleanA);

    boolean getUnknownBooleanB();

    void setUnknownBooleanB(boolean unknownBooleanB);

    int getCollarColour();

    void setCollarColour(int collarColour);

}
