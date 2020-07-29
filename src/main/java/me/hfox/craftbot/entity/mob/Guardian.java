package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Monster;

public interface Guardian extends Monster {

    boolean isRetractingSpikes();

    void setRetractingSpikes(boolean retractingSpikes);

    int getTargetId();

    void setTargetId(int targetId);

}
