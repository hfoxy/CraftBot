package me.hfox.craftbot.entity.data;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.player.Gamemode;
import me.hfox.craftbot.protocol.play.server.data.player.PlayerInfoProperty;

import java.util.Optional;
import java.util.UUID;

public class PlayerInfo {

    private final UUID uuid;
    private final String name;
    private final PlayerInfoProperty[] properties;

    private Gamemode gamemode;
    private int ping;
    private boolean displayNameSet;
    private ChatComponent displayName;

    public PlayerInfo(UUID uuid, String name, PlayerInfoProperty[] properties) {
        this.uuid = uuid;
        this.name = name;
        this.properties = properties;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public PlayerInfoProperty[] getProperties() {
        return properties;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
    }

    public int getPing() {
        return ping;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public boolean isDisplayNameSet() {
        return displayNameSet;
    }

    public void setDisplayNameSet(boolean displayNameSet) {
        this.displayNameSet = displayNameSet;
    }

    public Optional<ChatComponent> getDisplayName() {
        return Optional.of(displayName);
    }

    public void setDisplayName(ChatComponent displayName) {
        this.displayName = displayName;
    }

}
