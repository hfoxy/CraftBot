package me.hfox.craftbot.world;

import me.hfox.craftbot.exception.world.BotUnknownRecipeException;

public enum RecipeType {

    CRAFTING_SHAPELESS("minecraft:crafting_shapeless"),
    CRAFTING_SHAPED("minecraft:crafting_shaped"),
    CRAFTING_SPECIAL_ARMOUR_DYE("minecraft:crafting_special_armordye"),
    CRAFTING_SPECIAL_BOOK_CLONING("minecraft:crafting_special_bookcloning"),
    CRAFTING_SPECIAL_MAP_CLONING("minecraft:crafting_special_mapcloning"),
    CRAFTING_SPECIAL_MAP_EXTENDING("minecraft:crafting_special_mapextending"),
    CRAFTING_SPECIAL_FIREWORK_ROCKET("minecraft:crafting_special_firework_rocket"),
    CRAFTING_SPECIAL_FIREWORK_STAR("minecraft:crafting_special_firework_star"),
    CRAFTING_SPECIAL_FIREWORK_STAR_FADE("minecraft:crafting_special_firework_star_fade"),
    CRAFTING_SPECIAL_REPAIR_ITEM("minecraft:crafting_special_repairitem"),
    CRAFTING_SPECIAL_TIPPED_ARROW("minecraft:crafting_special_tippedarrow"),
    CRAFTING_SPECIAL_BANNER_DUPLICATE("minecraft:crafting_special_bannerduplicate"),
    CRAFTING_SPECIAL_BANNER_ADD_PATTERN("minecraft:crafting_special_banneraddpattern"),
    CRAFTING_SPECIAL_SHIELD_DECORATION("minecraft:crafting_special_shielddecoration"),
    CRAFTING_SPECIAL_SHULKER_BOX_COLOURING("minecraft:crafting_special_shulkerboxcoloring"),
    CRAFTING_SPECIAL_SUSPICIOUS_STEW("minecraft:crafting_special_suspiciousstew"),
    SMELTING("minecraft:smelting"),
    BLASTING("minecraft:blasting"),
    SMOKING("minecraft:smoking"),
    CAMPFIRE_COOKING("minecraft:campfire_cooking"),
    STONE_CUTTING("minecraft:stonecutting");

    private final String typeId;

    RecipeType(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }

    public static RecipeType findByTypeId(String typeId) {
        for (RecipeType recipeType : values()) {
            if (recipeType.getTypeId().equalsIgnoreCase(typeId)) {
                return recipeType;
            }
        }

        throw new BotUnknownRecipeException(typeId);
    }

}
