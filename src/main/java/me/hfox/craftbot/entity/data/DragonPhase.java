package me.hfox.craftbot.entity.data;

import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;

public enum DragonPhase {

    CIRCLING,
    STRAFING,
    FLYING_TO_PORTAL,
    LANDING_ON_PORTAL,
    TAKING_OFF_PORTAL,
    LANDED_BREATH_ATTACK,
    LANDED_BREATH_ATTACK_FINDING_PLAYER,
    LANDED_PRE_BREATH_ATTACK_ROAR,
    CHARGING_PLAYER,
    FLYING_TO_DIE,
    HOVERING;

    public static DragonPhase findById(int id) {
        for (DragonPhase dragonPhase : values()) {
            if (dragonPhase.ordinal() == id) {
                return dragonPhase;
            }
        }

        throw new BotUnsupportedEntityException("Unknown dragon phase: " + id);
    }

}
