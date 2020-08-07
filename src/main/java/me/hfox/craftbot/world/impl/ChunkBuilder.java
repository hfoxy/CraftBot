package me.hfox.craftbot.world.impl;

import me.hfox.craftbot.world.Chunk;

@FunctionalInterface
public interface ChunkBuilder {

    Chunk build(int chunkX, int chunkZ);

}
