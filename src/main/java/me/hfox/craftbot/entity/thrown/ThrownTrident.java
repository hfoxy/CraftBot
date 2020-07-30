package me.hfox.craftbot.entity.thrown;

import me.hfox.craftbot.entity.Entity;

public interface ThrownTrident extends Entity {

    boolean isCritical();

    void setCritical(boolean critical);

    boolean isNoClip();

    void setNoClip(boolean noClip);

    byte getPiercingLevel();

    void setPiercingLevel(byte piercingLevel);

    byte getLoyaltyLevel();

    void setLoyaltyLevel(byte loyaltyLevel);

    boolean isEnchanted();

    void setEnchanted(boolean enchanted);

}
