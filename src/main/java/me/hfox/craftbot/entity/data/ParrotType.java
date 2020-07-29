package me.hfox.craftbot.entity.data;

import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;

public enum ParrotType {

    RED_BLUE,
    BLUE,
    GREEN,
    YELLOW_BLUE,
    GREY;

    public static ParrotType findById(int id) {
        for (ParrotType parrotType : values()) {
            if (parrotType.ordinal() == id) {
                return parrotType;
            }
        }

        throw new BotUnsupportedEntityException("Unknown parrot type: " + id);
    }

}
