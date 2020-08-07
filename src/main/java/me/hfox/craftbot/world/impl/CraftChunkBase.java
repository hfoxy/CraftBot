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

public abstract class CraftChunkBase implements Chunk {

    private final int x;
    private final int z;

    public CraftChunkBase(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
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
        return getBlockAtChunk(location.getChunkBlockX(), location.getBlockY(), location.getChunkBlockZ());
    }

    @Override
    public BlockStateDto getBlockAtChunk(int x, int y, int z) {
        int blockId = getBlockIdAtChunk(x, y, z);
        BlockStateDto state;
        if (blockId == -1) {
            state = BlockPalette.getAir();
        } else {
            state = BlockPalette
                    .findById(blockId)
                    .orElseThrow(() -> new BotUnknownBlockException(Integer.toString(blockId)));
        }

        if (state != null) {
            return state;
        }

        return BlockPalette.getAir();
    }

    protected abstract int getBlockIdAtChunk(int x, int y, int z);

    @Override
    public void setBlockAt(Location location, BlockStateDto blockState) {
        Preconditions.checkNotNull(location, "location should not be null");
        Preconditions.checkNotNull(blockState, "blockState should not be null");

        checkValid(location);
        setBlockAtChunkLocation(location.getChunkBlockX(), location.getBlockY(), location.getChunkBlockZ(), blockState);
    }

    @Override
    public void setBlockAtChunkLocation(int x, int y, int z, BlockStateDto blockState) {
        Preconditions.checkNotNull(blockState, "blockState should not be null");
        setBlockAtChunkLocation(x, y, z, blockState.getId());
    }

}
