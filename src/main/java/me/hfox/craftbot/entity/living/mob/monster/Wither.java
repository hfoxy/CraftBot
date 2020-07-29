package me.hfox.craftbot.entity.living.mob.monster;

public interface Wither extends Monster {

    int getCentreHeadTarget();

    void setCentreHeadTarget(int centreHeadTarget);

    int getLeftHeadTarget();

    void setLeftHeadTarget(int leftHeadTarget);

    int getRightHeadTarget();

    void setRightHeadTarget(int rightHeadTarget);

    int getInvulnerableTime();

    void setInvulnerableTime(int invulnerableTime);

}
