package me.hfox.craftbot.protocol.play.server.data.player;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.player.Gamemode;

import java.util.Optional;
import java.util.UUID;

public class PlayerInfoActionAddPlayerImpl extends BasePlayerInfoAction implements PlayerInfoActionAddPlayer {

    private final String name;
    private final PlayerInfoProperty[] properties;
    private final Gamemode gamemode;
    private final int ping;
    private final ChatComponent displayName;

    public PlayerInfoActionAddPlayerImpl(UUID uuid, String name, PlayerInfoProperty[] properties, Gamemode gamemode, int ping, ChatComponent displayName) {
        super(PlayerInfoActionType.ADD_PLAYER, uuid);
        this.name = name;
        this.properties = properties;
        this.gamemode = gamemode;
        this.ping = ping;
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PlayerInfoProperty[] getProperties() {
        return properties;
    }

    @Override
    public Gamemode getGamemode() {
        return gamemode;
    }

    @Override
    public int getPing() {
        return ping;
    }

    @Override
    public Optional<ChatComponent> getDisplayName() {
        return Optional.ofNullable(displayName);
    }

}
