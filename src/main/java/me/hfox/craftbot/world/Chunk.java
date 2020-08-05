package me.hfox.craftbot.world;

import me.hfox.craftbot.world.palette.BlockStateDto;

public interface Chunk {

    BlockStateDto getBlockAt(Location location);

    void setBlockAt(Location location, BlockStateDto blockState);

    void setBlockAtChunkLocation(int x, int y, int z, BlockStateDto blockState);

}
