package me.hfox.craftbot.entity.data;

import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;

public enum VillagerProfession {

    NONE("minecraft:none"),
    ARMOURER("minecraft:armorer"),
    BUTCHER("minecraft:butcher"),
    CARTOGRAPHER("minecraft:cartographer"),
    CLERIC("minecraft:cleric"),
    FARMER("minecraft:farmer"),
    FISHERMAN("minecraft:fisherman"),
    FLETCHER("minecraft:fletcher"),
    LEATHERWORKER("minecraft:leatherworker"),
    LIBRARIAN("minecraft:librarian"),
    MASON("minecraft:mason"),
    NITWIT("minecraft:nitwit"),
    SHEPHERD("minecraft:shepherd"),
    TOOLSMITH("minecraft:toolsmith"),
    WEAPONSMITH("minecraft:weaponsmith");

    private final String name;

    VillagerProfession(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static VillagerProfession findById(int id) {
        for (VillagerProfession villagerProfession : values()) {
            if (villagerProfession.ordinal() == id) {
                return villagerProfession;
            }
        }

        throw new BotUnsupportedEntityException("Unknown villager profession: " + id);
    }

}
