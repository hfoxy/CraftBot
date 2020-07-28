package me.hfox.craftbot.entity;

public interface MobEntity extends LivingEntity {

    boolean hasAi();

    void setAi(boolean ai);

    Hand getMainHand();

    void setMainHand(Hand mainHand);

}
