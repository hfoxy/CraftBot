package me.hfox.craftbot.player;

import me.hfox.craftbot.exception.player.BotUnknownGamemodeException;

public enum Gamemode {

    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    public static Gamemode findById(int id) {
        if (id < 0 || id >= values().length) {
            throw new BotUnknownGamemodeException(Integer.toString(id));
        }

        return values()[id];
    }

}
