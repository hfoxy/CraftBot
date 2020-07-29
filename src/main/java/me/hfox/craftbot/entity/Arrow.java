package me.hfox.craftbot.entity;

public interface Arrow extends Entity {

    boolean isCritical();

    void setCritical(boolean critical);

    boolean isNoClip();

    void setNoClip(boolean noClip);

    byte getPiercingLevel();

    void setPiercingLevel(byte piercingLevel);

    int getColour();

    void setColour(int colour);

}
