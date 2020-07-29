package me.hfox.craftbot.entity.data;

import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;

public enum VillagerType {

    DESERT("minecraft:desert"),
    JUNGLE("minecraft:jungle"),
    PLAINS("minecraft:plains"),
    SAVANNA("minecraft:savanna"),
    SNOW("minecraft:snow"),
    SWAMP("minecraft:swamp"),
    TAIGA("minecraft:taiga");

    private final String name;

    VillagerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static VillagerType findById(int id) {
        for (VillagerType villagerType : values()) {
            if (villagerType.ordinal() == id) {
                return villagerType;
            }
        }

        throw new BotUnsupportedEntityException("Unknown villager type: " + id);
    }

}
