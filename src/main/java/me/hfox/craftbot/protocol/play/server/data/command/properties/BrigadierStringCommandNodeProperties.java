package me.hfox.craftbot.protocol.play.server.data.command.properties;

import me.hfox.craftbot.protocol.play.server.data.command.CommandNodeProperties;

public class BrigadierStringCommandNodeProperties implements CommandNodeProperties {

    private final BrigadierStringCommandNodePropertiesType type;

    public BrigadierStringCommandNodeProperties(BrigadierStringCommandNodePropertiesType type) {
        this.type = type;
    }

    public BrigadierStringCommandNodePropertiesType getType() {
        return type;
    }

    public enum BrigadierStringCommandNodePropertiesType {

        SINGLE_WORD,
        QUOTABLE_PHRASE,
        GREEDY_PHRASE

    }

}
