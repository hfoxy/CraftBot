package me.hfox.craftbot.entity.living.mob.animal.tameable;

import me.hfox.craftbot.entity.data.CatType;

public interface Cat extends Tameable {

    CatType getType();

    void setType(CatType catType);

    boolean getUnknownBooleanA();

    void setUnknownBooleanA(boolean unknownBooleanA);

    boolean getUnknownBooleanB();

    void setUnknownBooleanB(boolean unknownBooleanB);

    int getCollarColour();

    void setCollarColour(int collarColour);

}
