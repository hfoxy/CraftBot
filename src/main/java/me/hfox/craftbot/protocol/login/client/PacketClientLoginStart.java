package me.hfox.craftbot.protocol.login.client;

import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketClientLoginStart implements ClientPacket {

    private String username;

    public PacketClientLoginStart(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void write(ProtocolBuffer buffer) {
        buffer.writeString(username);
    }

}
