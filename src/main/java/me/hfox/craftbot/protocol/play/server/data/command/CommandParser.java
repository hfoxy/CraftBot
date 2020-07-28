package me.hfox.craftbot.protocol.play.server.data.command;

import me.hfox.craftbot.exception.command.BotUnknownCommandParserException;

public enum CommandParser {
    
    BRIGADIER_BOOL("brigadier:bool"),
    BRIGADIER_DOUBLE("brigadier:double"),
    BRIGADIER_FLOAT("brigadier:float"),
    BRIGADIER_INTEGER("brigadier:integer"),
    BRIGADIER_STRING("brigadier:string"),
    ENTITY("minecraft:entity"),
    GAME_PROFILE("minecraft:game_profile"),
    BLOCK_POS("minecraft:block_pos"),
    COLUMN_POS("minecraft:column_pos"),
    VEC3("minecraft:vec3"),
    VEC2("minecraft:vec2"),
    BLOCK_STATE("minecraft:block_state"),
    BLOCK_PREDICATE("minecraft:block_predicate"),
    ITEM_STACK("minecraft:item_stack"),
    ITEM_PREDICATE("minecraft:item_predicate"),
    COLOUR("minecraft:color"),
    COMPONENT("minecraft:component"),
    MESSAGE("minecraft:message"),
    NBT("minecraft:nbt"),
    NBT_PATH("minecraft:nbt_path"),
    OBJECTIVE("minecraft:objective"),
    OBJECTIVE_CRITERIA("minecraft:objective_criteria"),
    OPERATION("minecraft:operation"),
    PARTICLE("minecraft:particle"),
    ROTATION("minecraft:rotation"),
    SCOREBOARD_SLOT("minecraft:scoreboard_slot"),
    SCORE_HOLDER("minecraft:score_holder"),
    SWIZZLE("minecraft:swizzle"),
    TEAM("minecraft:team"),
    ITEM_SLOT("minecraft:item_slot"),
    RESOURCE_LOCATION("minecraft:resource_location"),
    MOB_EFFECT("minecraft:mob_effect"),
    FUNCTION("minecraft:function"),
    ENTITY_ANCHOR("minecraft:entity_anchor"),
    RANGE("minecraft:range"),
    INT_RANGE("minecraft:int_range"),
    FLOAT_RANGE("minecraft:float_range"),
    ITEM_ENCHANTMENT("minecraft:item_enchantment"),
    ENTITY_SUMMON("minecraft:entity_summon"),
    DIMENSION("minecraft:dimension"),
    UUID("minecraft:uuid"),
    NBT_TAG("minecraft:nbt_tag"),
    NBT_TAG_COMPOUND("minecraft:nbt_tag_compound"),
    TIME("minecraft:time");

    private final String identifier;

    CommandParser(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static CommandParser findByIdentifier(String typeId) {
        for (CommandParser parser : values()) {
            if (parser.getIdentifier().equalsIgnoreCase(typeId)) {
                return parser;
            }
        }

        throw new BotUnknownCommandParserException(typeId);
    }

}
