package me.hfox.craftbot.protocol.status.server.data;

import java.util.UUID;

public class ServerListPingResponsePlayerSample {

    private String name;
    private String uuidString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuidString() {
        return uuidString;
    }

    public void setUuidString(String uuidString) {
        this.uuidString = uuidString;
    }

    public UUID getUuid() {
        return UUID.fromString(uuidString);
    }

}
