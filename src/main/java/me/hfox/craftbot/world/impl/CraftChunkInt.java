package me.hfox.craftbot.world.impl;

import me.hfox.craftbot.exception.world.BotIllegalBlockException;

public class CraftChunkInt extends CraftChunkBase {

    private final int[][][] blocks;

    public CraftChunkInt(int x, int z) {
        super(x, z);
        this.blocks = new int[16][256][16];
    }

    @Override
    protected int getBlockIdAtChunk(int x, int y, int z) {
        return blocks[x][y][z];
    }

    @Override
    public void setBlockAtChunkLocation(int x, int y, int z, int blockId) {
        if (blockId > Short.MAX_VALUE) {
            throw new BotIllegalBlockException(blockId + " is out of short bounds");
        }

        blocks[x][y][z] = (short) blockId;
    }

}
