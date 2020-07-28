package me.hfox.craftbot.protocol.play.server.data.player;

import java.util.UUID;

public class PlayerInfoActionLatencyImpl extends BasePlayerInfoAction implements PlayerInfoActionLatency {

    private final int ping;

    public PlayerInfoActionLatencyImpl(UUID uuid, int ping) {
        super(PlayerInfoActionType.UPDATE_GAMEMODE, uuid);
        this.ping = ping;
    }

    @Override
    public int getPing() {
        return ping;
    }

}
