package me.hfox.craftbot.entity.mob.monster;

import me.hfox.craftbot.entity.mob.monster.Monster;

import java.util.Optional;

public interface Enderman extends Monster {

    Optional<Integer> getCarriedBlock();

    void setCarriedBlock(Integer carriedBlock);

    boolean isScreaming();

    void setScreaming(boolean screaming);

    boolean isStaredAt();

    void setStaredAt(boolean staredAt);

}
