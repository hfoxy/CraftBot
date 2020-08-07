package me.hfox.craftbot.handling;

import io.netty.buffer.Unpooled;
import me.hfox.craftbot.exception.world.BotUnknownBlockException;
import me.hfox.craftbot.protocol.chunk.ChunkStream;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayChunkData;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Chunk;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.palette.BlockPalette;
import me.hfox.craftbot.world.palette.BlockStateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ChunkReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkReader.class);

    private static final int CHUNK_HEIGHT = 16;

    private final WorldHandler worldHandler;

    public ChunkReader(WorldHandler worldHandler) {
        this.worldHandler = worldHandler;
    }

    private Chunk loadChunk(int chunkX, int chunkZ) {
        return worldHandler.getWorld().loadChunk(chunkX, chunkZ);
    }

    public void readChunk(PacketServerPlayChunkData packet) {
        Chunk chunk = loadChunk(packet.getChunkX(), packet.getChunkZ());

        int primaryBitMask = packet.getPrimaryBitMask();
        List<Integer> chunks = new ArrayList<>();
        for (int i = 0; i < CHUNK_HEIGHT; i++) {
            if ((primaryBitMask & 0x01) == 0x01) {
                chunks.add(i);
            }

            primaryBitMask = primaryBitMask >> 1;
        }

        ProtocolBuffer buffer = new ProtocolBuffer(Unpooled.copiedBuffer(packet.getData()));
        for (Integer chunkId : chunks) {
            short blocks = buffer.readShort();
            short bitsPerBlock = buffer.readUnsignedByte();

            int[] palette = new int[buffer.readVarInt()];
            for (int i = 0; i < palette.length; i++) {
                palette[i] = buffer.readVarInt();
            }

            LOGGER.debug("Info for [x={}, y={}, z={}] | Blocks: {} | Bits per Block: {} | Palette ({}): {}", packet.getChunkX(), chunkId, packet.getChunkZ(), blocks, bitsPerBlock, palette.length, palette);

            int dataLength = buffer.readVarInt();
            long[] data = new long[dataLength];

            for (int i = 0; i < dataLength; i++) {
                data[i] = buffer.readLong();
            }

            ChunkStream stream = new ChunkStream(data, bitsPerBlock);

            // int baseChunkX = packet.getChunkX() * 16;
            int baseChunkY = chunkId * 16;
            // int baseChunkZ = packet.getChunkZ() * 16;

            try {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        for (int x = 0; x < 16; x++) {
                            setBlock(chunk, palette, stream.read(), x, y + baseChunkY, z);
                        }
                    }
                }
            } catch (Exception ex) {
                LOGGER.error("Unable to parse chunk", ex);
            }

            LOGGER.debug("{} readable bits remain", stream.readableBits());
        }
    }

    private void setBlock(Chunk chunk, int[] palette, int paletteBlockId, int x, int y, int z) {
        int blockId = palette[paletteBlockId];
        // BlockStateDto blockState = BlockPalette.findById(blockId).orElseThrow(() -> new BotUnknownBlockException(Integer.toString(blockId)));
        // chunk.setBlockAtChunkLocation(location);
        // worldHandler.getWorld().setBlock(location, blockState);
        chunk.setBlockAtChunkLocation(x, y, z, blockId);
    }

}
