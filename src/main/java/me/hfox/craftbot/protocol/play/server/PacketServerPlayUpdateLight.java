package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PacketServerPlayUpdateLight implements ServerPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketServerPlayUpdateLight.class);
    private static final int CHUNK_HEIGHT = 256 / 16;
    private static final int LIGHT_CHUNK_HEIGHT = CHUNK_HEIGHT + 2;

    private int chunkX;
    private int chunkZ;

    private int skyLightMask;
    private int blockLightMask;
    private int emptySkyLightMask;
    private int emptyBlockLightMask;

    private byte[][] skyLight;
    private byte[][] blockLight;

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public int getSkyLightMask() {
        return skyLightMask;
    }

    public int getBlockLightMask() {
        return blockLightMask;
    }

    public int getEmptySkyLightMask() {
        return emptySkyLightMask;
    }

    public int getEmptyBlockLightMask() {
        return emptyBlockLightMask;
    }

    public byte[][] getSkyLight() {
        return skyLight;
    }

    public byte[][] getBlockLight() {
        return blockLight;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        chunkX = buffer.readVarInt();
        chunkZ = buffer.readVarInt();
        LOGGER.debug("Found light information on Chunk [x={}, z={}]", chunkX, chunkZ);

        skyLightMask = buffer.readVarInt();
        blockLightMask = buffer.readVarInt();
        LOGGER.debug("                                 [skyLightMask={}, blockLightMask={}]", skyLightMask, blockLightMask);

        emptySkyLightMask = buffer.readVarInt();
        emptyBlockLightMask = buffer.readVarInt();
        LOGGER.debug("                                 [emptySkyLightMask={}, emptyBlockLightMask={}]", emptySkyLightMask, emptyBlockLightMask);

        // 18 chunks, 16 editable chunks, 1 void chunk and 1 sky chunk outside of height limit
        skyLight = getLight(buffer, skyLightMask, emptyBlockLightMask);
        blockLight = getLight(buffer, blockLightMask, emptyBlockLightMask);
        LOGGER.debug("                                 [skyLightBytes={}, blockLightBytes={}]", skyLight.length, blockLight.length);
    }

    private byte[][] getLight(ProtocolBuffer buffer, int mask, int emptyMask) {
        List<Integer> blockLightChunkPresence = getChunkPresence(mask, emptyMask);
        byte[][] light = new byte[blockLightChunkPresence.size()][];
        for (int i = 0; i < light.length; i++) {
            byte[] content = new byte[buffer.readVarInt()];
            buffer.readBytes(content);
            light[i] = content;
        }

        return light;
    }

    private List<Integer> getChunkPresence(int mask, int emptyMask) {
        List<Integer> chunks = new ArrayList<>();
        for (int i = -1; i < LIGHT_CHUNK_HEIGHT; i++) {
            boolean maskValue = (mask & 0x01) == 0x01;
            boolean emptyMaskValue = (emptyMask & 0x01) == 0x01;

            if (maskValue) {
                chunks.add(i);
            }

            mask = mask >> 1;
            emptyMask = emptyMask >> 1;
        }

        return chunks;
    }

}
