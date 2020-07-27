package me.hfox.craftbot.protocol.login.server;

import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.util.UUID;

public class PacketServerLoginEncryptionRequest implements ServerPacket {

    private String serverId;
    private byte[] publicKey;
    private byte[] verifyToken;

    public String getServerId() {
        return serverId;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        serverId = buffer.readString();
        publicKey = buffer.readBytes(buffer.readVarInt()).array();
        verifyToken = buffer.readBytes(buffer.readVarInt()).array();
    }

}
