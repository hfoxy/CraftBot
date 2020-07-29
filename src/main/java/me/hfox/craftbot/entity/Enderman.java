package me.hfox.craftbot.entity;

import java.util.Optional;

public interface Enderman extends Monster {

    Optional<Integer> getCarriedBlock();

    void setCarriedBlock(Integer carriedBlock);

    boolean isScreaming();

    void setScreaming(boolean screaming);

    boolean isStaredAt();

    void setStaredAt(boolean staredAt);

}
