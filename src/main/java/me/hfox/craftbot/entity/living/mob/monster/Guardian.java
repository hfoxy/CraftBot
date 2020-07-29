package me.hfox.craftbot.entity.living.mob.monster;

public interface Guardian extends Monster {

    boolean isRetractingSpikes();

    void setRetractingSpikes(boolean retractingSpikes);

    int getTargetId();

    void setTargetId(int targetId);

}
