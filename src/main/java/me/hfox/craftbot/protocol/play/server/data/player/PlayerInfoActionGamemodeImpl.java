package me.hfox.craftbot.protocol.play.server.data.player;

import me.hfox.craftbot.player.Gamemode;

import java.util.UUID;

public class PlayerInfoActionGamemodeImpl extends BasePlayerInfoAction implements PlayerInfoActionGamemode {

    private final Gamemode gamemode;

    public PlayerInfoActionGamemodeImpl(UUID uuid, Gamemode gamemode) {
        super(PlayerInfoActionType.UPDATE_GAMEMODE, uuid);
        this.gamemode = gamemode;
    }

    @Override
    public Gamemode getGamemode() {
        return gamemode;
    }

}
