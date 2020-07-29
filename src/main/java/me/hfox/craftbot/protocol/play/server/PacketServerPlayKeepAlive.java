package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayKeepAlive implements ServerPacket {

    private long keepAliveId;

    public long getKeepAliveId() {
        return keepAliveId;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        keepAliveId = buffer.readLong();
    }

}
