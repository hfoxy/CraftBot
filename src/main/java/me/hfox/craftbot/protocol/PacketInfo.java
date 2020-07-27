package me.hfox.craftbot.protocol;

import me.hfox.craftbot.protocol.handshake.client.ProtocolState;

public class PacketInfo {

    private ProtocolState state;
    private int packetId;
    private Class<? extends Packet> packetType;

    public PacketInfo(ProtocolState state, int packetId, Class<? extends Packet> packetType) {
        this.state = state;
        this.packetId = packetId;
        this.packetType = packetType;
    }

    public ProtocolState getState() {
        return state;
    }

    public int getPacketId() {
        return packetId;
    }

    public Class<? extends Packet> getPacketType() {
        return packetType;
    }

}
