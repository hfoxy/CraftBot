package me.hfox.craftbot.entity.minecart;

import me.hfox.craftbot.entity.Entity;

public interface MinecartBase extends Entity {

    int getShakingPower();

    void setShakingPower(int shakingPower);

    int getShakingDirection();

    void setShakingDirection(int shakingDirection);

    float getShakingMultiplier();

    void setShakingMultiplier(int shakingMultiplier);

    int getBlock();

    void setBlock(int block);

    int getBlockY();

    void setBlockY(int blockY);

    boolean isCustomBlockShown();

    void setCustomBlockShown(boolean customBlockShown);

}
