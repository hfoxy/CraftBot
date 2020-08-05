package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayUpdateViewDistance implements ServerPacket {

    private int viewDistance;

    public int getViewDistance() {
        return viewDistance;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        viewDistance = buffer.readVarInt();
    }

}
