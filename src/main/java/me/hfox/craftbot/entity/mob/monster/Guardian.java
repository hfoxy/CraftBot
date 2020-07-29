package me.hfox.craftbot.entity.mob.monster;

import me.hfox.craftbot.entity.mob.monster.Monster;

public interface Guardian extends Monster {

    boolean isRetractingSpikes();

    void setRetractingSpikes(boolean retractingSpikes);

    int getTargetId();

    void setTargetId(int targetId);

}
