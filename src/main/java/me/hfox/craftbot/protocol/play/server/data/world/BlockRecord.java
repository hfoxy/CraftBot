package me.hfox.craftbot.protocol.play.server.data.world;

import me.hfox.craftbot.world.palette.BlockStateDto;

public class BlockRecord {

    private final short relativeChunkX;
    private final short relativeChunkZ;
    private final short y;
    private final BlockStateDto blockState;

    public BlockRecord(short relativeChunkX, short relativeChunkZ, short y, BlockStateDto blockState) {
        this.relativeChunkX = relativeChunkX;
        this.relativeChunkZ = relativeChunkZ;
        this.y = y;
        this.blockState = blockState;
    }

    public short getRelativeChunkX() {
        return relativeChunkX;
    }

    public short getRelativeChunkZ() {
        return relativeChunkZ;
    }

    public short getY() {
        return y;
    }

    public BlockStateDto getBlockState() {
        return blockState;
    }

}
