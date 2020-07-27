package me.hfox.craftbot.world;

import me.hfox.craftbot.exception.world.BotUnknownDimensionException;

public enum Dimension {

    NETHER(-1),
    OVERWORLD(0),
    END(1);

    private final int id;

    Dimension(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Dimension findById(int id) {
        for (Dimension dimension : values()) {
            if (dimension.getId() == id) {
                return dimension;
            }
        }

        throw new BotUnknownDimensionException(Integer.toString(id));
    }

}
