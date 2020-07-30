package me.hfox.craftbot.entity.living.mob.monster;

import me.hfox.craftbot.world.palette.BlockStateDto;

import java.util.Optional;

public interface Enderman extends Monster {

    Optional<BlockStateDto> getCarriedBlock();

    void setCarriedBlock(BlockStateDto carriedBlock);

    boolean isScreaming();

    void setScreaming(boolean screaming);

    boolean isStaredAt();

    void setStaredAt(boolean staredAt);

}
