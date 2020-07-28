package me.hfox.craftbot.protocol.play.server.data.command.properties;

import me.hfox.craftbot.protocol.play.server.data.command.CommandNodeProperties;

public class EntityCommandNodeProperties implements CommandNodeProperties {

    private final boolean single;
    private final boolean players;

    public EntityCommandNodeProperties(boolean single, boolean players) {
        this.single = single;
        this.players = players;
    }

    public boolean isSingle() {
        return single;
    }

    public boolean isPlayers() {
        return players;
    }

}
