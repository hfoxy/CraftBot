package me.hfox.craftbot.world.impl;

import me.hfox.craftbot.exception.world.BotIllegalBlockException;

public class CraftChunkShort extends CraftChunkBase {

    private final short[][][] blocks;

    public CraftChunkShort(int x, int z) {
        super(x, z);
        this.blocks = new short[16][256][16];
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
