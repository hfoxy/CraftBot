package me.hfox.craftbot.world.impl;

import com.google.common.base.Preconditions;
import me.hfox.craftbot.exception.world.BotUnknownBlockException;
import me.hfox.craftbot.world.Chunk;
import me.hfox.craftbot.world.Location;
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
        StringJoiner invalid = new StringJoiner(", ", "[", "]");
        if (location.getChunkX() != x) {
            invalid.add("x=" + location.getBlockX());
            fail = true;
        }

        if (location.getBlockY() < 0 || location.getBlockY() >= 256) {
            invalid.add("y=" + location.getBlockY());
            fail = true;
        }

        if (location.getChunkZ() != z) {
            invalid.add("z=" + location.getBlockZ());
            fail = true;
        }

        if (fail) {
            throw new BotUnknownBlockException("Bad location: " + invalid);
        }
    }

    @Override
    public BlockStateDto getBlockAt(Location location) {
        checkValid(location);
        // LOGGER.info("blocks... {} -{}", blocks, "done");
        return blocks[location.getChunkBlockX()][location.getBlockY()][location.getChunkBlockZ()];
    }

    @Override
    public void setBlockAt(Location location, BlockStateDto blockState) {
        Preconditions.checkNotNull(location, "location should not be null");
        Preconditions.checkNotNull(blockState, "blockState should not be null");

        checkValid(location);

        if (!blockState.getBlock().getIdentifier().equalsIgnoreCase("minecraft:air")) {
            // LOGGER.info("Setting block @ {} to '{}'", location, blockState.getBlock().getIdentifier());
        }

        blocks[location.getChunkBlockX()][location.getBlockY()][location.getChunkBlockZ()] = blockState;
    }

}
