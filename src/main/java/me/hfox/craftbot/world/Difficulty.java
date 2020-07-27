package me.hfox.craftbot.world;

import me.hfox.craftbot.exception.world.BotUnknownDifficultyException;

public enum Difficulty {

    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    public static Difficulty findById(int id) {
        if (id < 0 || id >= values().length) {
            throw new BotUnknownDifficultyException(Integer.toString(id));
        }

        return values()[id];
    }

}
