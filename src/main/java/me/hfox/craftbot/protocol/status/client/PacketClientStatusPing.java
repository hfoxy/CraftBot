package me.hfox.craftbot.protocol.status.client;

import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketClientStatusPing implements ClientPacket {

    private long payload;

    public PacketClientStatusPing(long payload) {
        this.payload = payload;
    }

    @Override
    public void write(ProtocolBuffer buffer) {
        buffer.writeLong(payload);
    }

}
