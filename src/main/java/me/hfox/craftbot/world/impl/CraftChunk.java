package me.hfox.craftbot.world.impl;

import com.google.common.base.Preconditions;
import me.hfox.craftbot.exception.world.BotUnknownBlockException;
import me.hfox.craftbot.world.Chunk;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.palette.BlockPalette;
import me.hfox.craftbot.world.palette.BlockStateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringJoiner;

public class CraftChunk implements Chunk {

    private static final Logger LOGGER = LoggerFactory.getLogger(CraftChunk.class);

    private final int x;
    private final int z;
    private final BlockStateDto[][][] blocks;

    public CraftChunk(int x, int z) {
        this.x = x;
        this.z = z;
        this.blocks = new BlockStateDto[16][256][16];
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public BlockStateDto[][][] getBlocks() {
        return blocks;
    }

    private void checkValid(Location location) {
        boolean fail = false;
        if (location.getChunkX() != x) {
            fail = true;
        }

        if (location.getBlockY() < 0 || location.getBlockY() >= 256) {
            fail = true;
        }

        if (location.getChunkZ() != z) {
            fail = true;
        }

        if (fail) {
            StringJoiner invalid = new StringJoiner(", ", "[", "]");
            if (location.getChunkX() != x) {
                invalid.add("x=" + location.getBlockX());
            }

            if (location.getBlockY() < 0 || location.getBlockY() >= 256) {
                invalid.add("y=" + location.getBlockY());
            }

            if (location.getChunkZ() != z) {
                invalid.add("z=" + location.getBlockZ());
            }

            throw new BotUnknownBlockException("Bad location: " + invalid);
        }
    }

    @Override
    public BlockStateDto getBlockAt(Location location) {
        try {
            checkValid(location);
        } catch (Exception ex) {
            return null;
        }

        // LOGGER.info("blocks... {} -{}", blocks, "done");

        BlockStateDto state = blocks[location.getChunkBlockX()][location.getBlockY()][location.getChunkBlockZ()];
        if (state != null) {
            return state;
        }

        return BlockPalette.getAir();
    }

    @Override
    public void setBlockAt(Location location, BlockStateDto blockState) {
        Preconditions.checkNotNull(location, "location should not be null");
        Preconditions.checkNotNull(blockState, "blockState should not be null");

        checkValid(location);
        _setBlock(location, blockState);
    }

    private synchronized void _setBlock(Location location, BlockStateDto blockState) {
        blocks[location.getChunkBlockX()][location.getBlockY()][location.getChunkBlockZ()] = blockState;
    }

}
