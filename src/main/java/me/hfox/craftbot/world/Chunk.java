package me.hfox.craftbot.world;

import me.hfox.craftbot.world.palette.BlockStateDto;

public interface Chunk {

    BlockStateDto getBlockAt(Location location);

    BlockStateDto getBlockAtChunk(int x, int y, int z);

    void setBlockAt(Location location, BlockStateDto blockState);

    void setBlockAtChunkLocation(int x, int y, int z, int blockId);

    void setBlockAtChunkLocation(int x, int y, int z, BlockStateDto blockState);

}
