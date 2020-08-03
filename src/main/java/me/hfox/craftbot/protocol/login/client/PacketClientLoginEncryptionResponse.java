package me.hfox.craftbot.protocol.login.client;

import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketClientLoginEncryptionResponse implements ClientPacket {

    private byte[] sharedSecret;
    private byte[] verifyToken;

    public PacketClientLoginEncryptionResponse(byte[] sharedSecret, byte[] verifyToken) {
        this.sharedSecret = sharedSecret;
        this.verifyToken = verifyToken;
    }

    public byte[] getSharedSecret() {
        return sharedSecret;
    }

    public byte[] getVerifyToken() {
        return verifyToken;
    }

    @Override
    public void write(ProtocolBuffer buffer) {
        buffer.writeByteArray(sharedSecret);
        buffer.writeByteArray(verifyToken);
    }

}
