package me.hfox.craftbot.protocol.status.server.data;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.chat.StringSupportedChatComponent;

public class ServerListPingResponse {

    private ServerListPingResponseVersion version;
    private ServerListPingResponsePlayers players;
    private StringSupportedChatComponent description;
    private String favicon;

    public ServerListPingResponseVersion getVersion() {
        return version;
    }

    public void setVersion(ServerListPingResponseVersion version) {
        this.version = version;
    }

    public ServerListPingResponsePlayers getPlayers() {
        return players;
    }

    public void setPlayers(ServerListPingResponsePlayers players) {
        this.players = players;
    }

    public StringSupportedChatComponent getDescription() {
        return description;
    }

    public void setDescription(StringSupportedChatComponent description) {
        this.description = description;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

}
