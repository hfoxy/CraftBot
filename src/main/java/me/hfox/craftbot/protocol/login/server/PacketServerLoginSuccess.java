package me.hfox.craftbot.protocol.login.server;

import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.util.UUID;

public class PacketServerLoginSuccess implements ServerPacket {

    private String uuidString;
    private UUID uuid;

    private String username;

    public String getUuidString() {
        return uuidString;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        uuidString = buffer.readString();
        uuid = UUID.fromString(uuidString);
        username = buffer.readString();
    }

}
