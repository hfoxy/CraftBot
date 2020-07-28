package me.hfox.craftbot.entity;

public interface CatEntity extends TameableEntity {

    CatType getType();

    void setCatType(CatType catType);

    boolean getUnknownBooleanA();

    void setUnknownBooleanA(boolean unknownBooleanA);

    boolean getUnknownBooleanB();

    void setUnknownBooleanB(boolean unknownBooleanB);

    int getCollarColour();

    void setCollarColour(int collarColour);

}
