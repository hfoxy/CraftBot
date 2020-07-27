package me.hfox.craftbot.protocol.status.server.data;

public class ServerListPingResponseVersion {

    private String name;
    private int protocol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

}
