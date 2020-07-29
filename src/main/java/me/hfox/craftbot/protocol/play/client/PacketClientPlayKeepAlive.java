package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketClientPlayKeepAlive implements ClientPacket {

    private long keepAliveId;

    public PacketClientPlayKeepAlive(long keepAliveId) {
        this.keepAliveId = keepAliveId;
    }

    public long getKeepAliveId() {
        return keepAliveId;
    }

    @Override
    public void write(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        buffer.writeLong(keepAliveId);
    }

}
