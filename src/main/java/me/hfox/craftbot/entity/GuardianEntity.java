package me.hfox.craftbot.entity;

public interface GuardianEntity extends MonsterEntity {

    boolean isRetractingSpikes();

    void setRetractingSpikes(boolean retractingSpikes);

    int getTargetId();

    void setTargetId(int targetId);

}
