package me.hfox.craftbot.world;

import me.hfox.craftbot.exception.world.BotUnknownLevelTypeException;

public enum LevelType {

    DEFAULT("default"),
    FLAT("flat"),
    LARGE_BIOMES("largeBiomes"),
    AMPLIFIED("amplified"),
    CUSTOMIZED("customized"),
    BUFFET("buffet"),
    DEFAULT_1_1("default_1_1");

    private final String name;

    LevelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LevelType findByName(String name) {
        for (LevelType type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }

        throw new BotUnknownLevelTypeException(name);
    }

}
