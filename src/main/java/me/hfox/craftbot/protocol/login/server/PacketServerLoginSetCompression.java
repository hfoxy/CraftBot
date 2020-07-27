package me.hfox.craftbot.protocol.login.server;

import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.util.UUID;

public class PacketServerLoginSetCompression implements ServerPacket {

    private int threshold;

    public int getThreshold() {
        return threshold;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        threshold = buffer.readVarInt();
    }

}
