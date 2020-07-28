package me.hfox.craftbot.protocol.play.server.data.command;

import java.util.Optional;

public class CommandNode {

    private final CommandNodeFlags flags;
    private final int[] childIndexes;
    private final Integer redirectNode;
    private final String name;
    private final CommandParser parser;
    private final CommandNodeProperties properties;
    private final CommandNodeSuggestionsType suggestionsType;

    public CommandNode(CommandNodeFlags flags, int[] childIndexes, Integer redirectNode, String name, CommandParser parser, CommandNodeProperties properties, CommandNodeSuggestionsType suggestionsType) {
        this.flags = flags;
        this.childIndexes = childIndexes;
        this.redirectNode = redirectNode;
        this.name = name;
        this.parser = parser;
        this.properties = properties;
        this.suggestionsType = suggestionsType;
    }

    public CommandNodeFlags getFlags() {
        return flags;
    }

    public int[] getChildIndexes() {
        return childIndexes;
    }

    public Optional<Integer> getRedirectNode() {
        return Optional.ofNullable(redirectNode);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<CommandParser> getParser() {
        return Optional.ofNullable(parser);
    }

    public Optional<CommandNodeProperties> getProperties() {
        return Optional.ofNullable(properties);
    }

    public Optional<CommandNodeSuggestionsType> getSuggestionsType() {
        return Optional.ofNullable(suggestionsType);
    }

}
