package me.hfox.craftbot.protocol.handshake.client;

import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketClientHandshake implements ClientPacket {

    private int protocolVersion;
    private String serverAddress;
    private int port;
    private ProtocolState nextState;

    public PacketClientHandshake() {
        // empty for reflection create
    }

    public PacketClientHandshake(int protocolVersion, String serverAddress, int port, ProtocolState nextState) {
        this.protocolVersion = protocolVersion;
        this.serverAddress = serverAddress;
        this.port = port;
        this.nextState = nextState;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getPort() {
        return port;
    }

    public ProtocolState getNextState() {
        return nextState;
    }

    @Override
    public void write(ProtocolBuffer buffer) {
        buffer.writeVarInt(protocolVersion);
        buffer.writeString(serverAddress);
        buffer.writeUnsignedShort(port);
        buffer.writeVarInt(nextState.ordinal());
    }

}
