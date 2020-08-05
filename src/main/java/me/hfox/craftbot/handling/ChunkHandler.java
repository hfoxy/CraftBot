package me.hfox.craftbot.handling;

import io.netty.buffer.Unpooled;
import me.hfox.craftbot.exception.world.BotChunkNotLoadedException;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChunkHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkHandler.class);

    private final BlockingQueue<Runnable> poolQueue;
    private final ThreadPoolExecutor poolExecutor;

    private final WorldHandler worldHandler;
    private final ChunkReader chunkReader;

    public ChunkHandler(WorldHandler worldHandler) {
        this.poolQueue = new LinkedBlockingQueue<>();
        this.poolExecutor = new ThreadPoolExecutor(1, 100, 1, TimeUnit.MINUTES, poolQueue);
        this.worldHandler = worldHandler;
        this.chunkReader = new ChunkReader(worldHandler);
    }

    public synchronized void onReceive(ServerPacket packet) {
        if (packet instanceof PacketServerPlayChunkData) {
            chunkReader.readChunk((PacketServerPlayChunkData) packet);
        } else if (packet instanceof PacketServerPlayUnloadChunk) {
            PacketServerPlayUnloadChunk unloadChunk = (PacketServerPlayUnloadChunk) packet;
            worldHandler.getWorld().unloadChunk(unloadChunk.getChunkX(), unloadChunk.getChunkZ());
        } else if (packet instanceof PacketServerPlayBlockChange) {
            PacketServerPlayBlockChange blockChange = (PacketServerPlayBlockChange) packet;

            try {
                worldHandler.getWorld().setBlock(blockChange.getPosition(), blockChange.getBlockState());
            } catch (BotChunkNotLoadedException ex) {
                // CHUNK ISN'T LOADED, WHO KNOWS WHAT TO DO!
                LOGGER.debug("Chunk not loaded: {}", ex.getMessage());
            }
        } else if (packet instanceof PacketServerPlayMultiBlockChange) {
            PacketServerPlayMultiBlockChange blockChange = (PacketServerPlayMultiBlockChange) packet;

            int chunkX = blockChange.getChunkX();
            int chunkZ = blockChange.getChunkZ();
            for (BlockRecord record : blockChange.getRecords()) {
                Location location = new Location(chunkX + record.getRelativeChunkX(), record.getY(), chunkZ + record.getRelativeChunkZ());

                try {
                    worldHandler.getWorld().setBlock(location, record.getBlockState());
                } catch (BotChunkNotLoadedException ex) {
                    // CHUNK ISN'T LOADED, WHO KNOWS WHAT TO DO!
                    LOGGER.debug("Chunk not loaded: {}", ex.getMessage());
                }
            }
        }
    }

}
