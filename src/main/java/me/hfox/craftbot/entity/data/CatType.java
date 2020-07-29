package me.hfox.craftbot.entity.data;

import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;

public enum CatType {

    TABBY,
    BLACK,
    RED,
    SIAMESE,
    BRITISH_SHORTHAIR,
    CALICO,
    PERSIAN,
    RAGDOLL,
    WHITE,
    ALL_BLACK;

    public static CatType findById(int id) {
        for (CatType catType : values()) {
            if (catType.ordinal() == id) {
                return catType;
            }
        }

        throw new BotUnsupportedEntityException("Unknown cat type: " + id);
    }

}
