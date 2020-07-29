package me.hfox.craftbot.entity;

public interface ThrownTrident extends Entity {

    boolean isCritical();

    void setCritical(boolean critical);

    boolean isNoClip();

    void setNoClip(boolean noClip);

    byte getPiercingLevel();

    void setPiercingLevel(byte piercingLevel);

    byte getLoyaltyLevel();

    void setLoyaltyLevel(byte loyaltyLevel);

    boolean hasEnchantment();

    void setEnchantment(boolean enchantment);

}
