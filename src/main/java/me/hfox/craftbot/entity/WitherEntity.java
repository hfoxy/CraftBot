package me.hfox.craftbot.entity;

public interface WitherEntity extends MonsterEntity {

    int getCentreHeadTarget();

    void setCentreHeadTarget(int centreHeadTarget);

    int getLeftHeadTarget();

    void setLeftHeadTarget(int leftHeadTarget);

    int getRightHeadTarget();

    void setRightHeadTarget(int rightHeadTarget);

    int getInvulnerableTime();

    void setInvulnerableTime(int invulnerableTime);

}
