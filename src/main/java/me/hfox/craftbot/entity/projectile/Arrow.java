package me.hfox.craftbot.entity.projectile;

import me.hfox.craftbot.entity.Entity;

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
