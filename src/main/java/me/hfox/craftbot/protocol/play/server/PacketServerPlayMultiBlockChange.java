package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.world.BlockRecord;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.palette.BlockStateDto;

import java.io.IOException;

public class PacketServerPlayMultiBlockChange implements ServerPacket {

    private int chunkX;
    private int chunkZ;
    private BlockRecord[] records;

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public BlockRecord[] getRecords() {
        return records;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        chunkX = buffer.readInt();
        chunkZ = buffer.readInt();
        records = new BlockRecord[buffer.readVarInt()];
        for (int i = 0; i < records.length; i++) {
            short horizontal = buffer.readUnsignedByte();
            short x = (short) ((horizontal & 0xF0) >> 4);
            short z = (short) (horizontal & 0xF);
            short y = buffer.readUnsignedByte();

            BlockStateDto blockState = buffer.readBlockState();
            records[i] = new BlockRecord(x, z, y, blockState);
        }
    }

}
