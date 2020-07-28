package me.hfox.craftbot.protocol.play.server.data.command;

import me.hfox.craftbot.exception.command.BotUnknownCommandNodeSuggestionsTypeException;

public enum CommandNodeSuggestionsType {

    ASK_SERVER("minecraft:ask_server"),
    ALL_RECIPES("minecraft:all_recipes"),
    AVAILABLE_SOUNDS("minecraft:available_sounds"),
    SUMMONABLE_ENTITIES("minecraft:summonable_entities");

    private final String identifier;

    CommandNodeSuggestionsType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public static CommandNodeSuggestionsType findByIdentifier(String typeId) {
        for (CommandNodeSuggestionsType suggestionsType : values()) {
            if (suggestionsType.getIdentifier().equalsIgnoreCase(typeId)) {
                return suggestionsType;
            }
        }

        throw new BotUnknownCommandNodeSuggestionsTypeException(typeId);
    }
}
