package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketClientPlayTeleportConfirm implements ClientPacket {

    private int teleportId;

    public PacketClientPlayTeleportConfirm(int teleportId) {
        this.teleportId = teleportId;
    }

    public int getTeleportId() {
        return teleportId;
    }

    @Override
    public void write(ProtocolBuffer buffer) {
        buffer.writeVarInt(teleportId);
    }

}
