package me.hfox.craftbot.protocol.status.server;

import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketServerStatusPong implements ServerPacket {

    private long payload;

    public long getPayload() {
        return payload;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        payload = buffer.readLong();
    }

}
