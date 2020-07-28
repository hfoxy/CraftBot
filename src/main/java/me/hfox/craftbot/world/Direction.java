package me.hfox.craftbot.world;

import me.hfox.craftbot.exception.world.BotUnknownDirectionException;

public enum Direction {

    DOWN,
    UP,
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public static Direction findById(int id) {
        for (Direction direction : values()) {
            if (direction.ordinal() == id) {
                return direction;
            }
        }

        throw new BotUnknownDirectionException(Integer.toString(id));
    }

}
