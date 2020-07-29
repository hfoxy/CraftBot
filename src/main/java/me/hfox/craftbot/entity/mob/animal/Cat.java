package me.hfox.craftbot.entity.mob.animal;

import me.hfox.craftbot.entity.data.CatType;

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
