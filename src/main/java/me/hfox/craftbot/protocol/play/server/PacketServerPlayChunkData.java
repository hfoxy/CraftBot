package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.nbt.Tag;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayChunkData implements ServerPacket {

    private int chunkX;
    private int chunkZ;

    private boolean fullChunk;
    private int primaryBitMask;
    private Tag heightmaps;

    private int[] biomes;
    private byte[] data;
    private Tag[] blocks;

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public boolean isFullChunk() {
        return fullChunk;
    }

    public int getPrimaryBitMask() {
        return primaryBitMask;
    }

    public Tag getHeightmaps() {
        return heightmaps;
    }

    public int[] getBiomes() {
        return biomes;
    }

    public byte[] getData() {
        return data;
    }

    public Tag[] getBlocks() {
        return blocks;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        chunkX = buffer.readInt();
        chunkZ = buffer.readInt();
        fullChunk = buffer.readBoolean();
        primaryBitMask = buffer.readVarInt();
        heightmaps = buffer.readTag();
        if (fullChunk) {
            biomes = new int[1024];
            for (int i = 0; i < biomes.length; i++) {
                biomes[i] = buffer.readInt();
            }
        }

        data = new byte[buffer.readVarInt()];
        for (int i = 0; i < data.length; i++) {
            data[i] = buffer.readByte();
        }

        blocks = new Tag[buffer.readVarInt()];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = buffer.readTag();
        }
    }

}
