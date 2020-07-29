package me.hfox.craftbot.entity.data;

import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;

public enum MooshroomType {

    RED,
    BROWN;

    public static MooshroomType findByName(String name) {
        for (MooshroomType mooshroomType : values()) {
            if (mooshroomType.name().equalsIgnoreCase(name)) {
                return mooshroomType;
            }
        }

        throw new BotUnsupportedEntityException("Unknown Mooshroom type: " + name);
    }

}
