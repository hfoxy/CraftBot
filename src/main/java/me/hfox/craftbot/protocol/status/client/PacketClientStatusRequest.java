package me.hfox.craftbot.protocol.status.client;

import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketClientStatusRequest implements ClientPacket {

    @Override
    public void write(ProtocolBuffer buffer) {
        // no fields
    }

}
