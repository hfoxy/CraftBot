package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayUnloadChunk implements ServerPacket {

    private int chunkX;
    private int chunkZ;

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        chunkX = buffer.readInt();
        chunkZ = buffer.readInt();
    }

}
