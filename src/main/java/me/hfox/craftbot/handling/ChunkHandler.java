package me.hfox.craftbot.handling;

import io.netty.buffer.Unpooled;
import me.hfox.craftbot.exception.world.BotUnknownBlockException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.chunk.ChunkStream;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayBlockChange;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayChunkData;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayMultiBlockChange;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayUnloadChunk;
import me.hfox.craftbot.protocol.play.server.data.world.BlockRecord;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.palette.BlockPalette;
import me.hfox.craftbot.world.palette.BlockStateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ChunkHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkHandler.class);

    private static final int CHUNK_HEIGHT = 16;

    private final WorldHandler worldHandler;

    public ChunkHandler(WorldHandler worldHandler) {
        this.worldHandler = worldHandler;
    }

    public void onReceive(ServerPacket packet) {
        if (packet instanceof PacketServerPlayChunkData) {
            PacketServerPlayChunkData chunkDataPacket = (PacketServerPlayChunkData) packet;
            worldHandler.getWorld().loadChunk(chunkDataPacket.getChunkX(), chunkDataPacket.getChunkZ());

            int primaryBitMask = chunkDataPacket.getPrimaryBitMask();
            List<Integer> chunks = new ArrayList<>();
            for (int i = 0; i < CHUNK_HEIGHT; i++) {
                if ((primaryBitMask & 0x01) == 0x01) {
                    chunks.add(i);
                }

                primaryBitMask = primaryBitMask >> 1;
            }

            ProtocolBuffer buffer = new ProtocolBuffer(Unpooled.copiedBuffer(chunkDataPacket.getData()));
            for (Integer chunkId : chunks) {
                short blocks = buffer.readShort();
                short bitsPerBlock = buffer.readUnsignedByte();

                int[] palette = new int[buffer.readVarInt()];
                for (int i = 0; i < palette.length; i++) {
                    palette[i] = buffer.readVarInt();
                }

                LOGGER.debug("Info for [x={}, y={}, z={}] | Blocks: {} | Bits per Block: {} | Palette ({}): {}", chunkDataPacket.getChunkX(), chunkId, chunkDataPacket.getChunkZ(), blocks, bitsPerBlock, palette.length, palette);

                List<Long> data = new ArrayList<>();
                int dataLength = buffer.readVarInt();

                for (int i = 0; i < dataLength; i++) {
                    data.add(buffer.readLong());
                }

                ChunkStream stream = new ChunkStream(data, bitsPerBlock);

                int baseChunkX = chunkDataPacket.getChunkX() * 16;
                int baseChunkY = chunkId * 16;
                int baseChunkZ = chunkDataPacket.getChunkZ() * 16;

                try {
                    for (int y = 0; y < 16; y++) {
                        for (int z = 0; z < 16; z++) {
                            for (int x = 0; x < 16; x++) {
                                Location location = new Location(x + baseChunkX, y + baseChunkY, z + baseChunkZ);

                                int blockId = palette[stream.read()];
                                BlockStateDto blockState = BlockPalette.findById(blockId).orElseThrow(() -> new BotUnknownBlockException(Integer.toString(blockId)));
                                worldHandler.getWorld().setBlock(location, blockState);
                            }
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.error("Unable to parse chunk", ex);
                }

                LOGGER.debug("{} readable bits remain", stream.readableBits());
            }
        } else if (packet instanceof PacketServerPlayUnloadChunk) {
            PacketServerPlayUnloadChunk unloadChunk = (PacketServerPlayUnloadChunk) packet;
            worldHandler.getWorld().unloadChunk(unloadChunk.getChunkX(), unloadChunk.getChunkZ());
        } else if (packet instanceof PacketServerPlayBlockChange) {
            PacketServerPlayBlockChange blockChange = (PacketServerPlayBlockChange) packet;
            worldHandler.getWorld().setBlock(blockChange.getPosition(), blockChange.getBlockState());
        } else if (packet instanceof PacketServerPlayMultiBlockChange) {
            PacketServerPlayMultiBlockChange blockChange = (PacketServerPlayMultiBlockChange) packet;

            int chunkX = blockChange.getChunkX();
            int chunkZ = blockChange.getChunkZ();
            for (BlockRecord record : blockChange.getRecords()) {
                Location location = new Location(chunkX + record.getRelativeChunkX(), record.getY(), chunkZ + record.getRelativeChunkZ());
                worldHandler.getWorld().setBlock(location, record.getBlockState());
            }
        }
    }

}
