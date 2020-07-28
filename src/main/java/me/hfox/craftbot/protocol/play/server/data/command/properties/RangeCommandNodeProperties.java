package me.hfox.craftbot.protocol.play.server.data.command.properties;

import me.hfox.craftbot.protocol.play.server.data.command.CommandNodeProperties;

public class RangeCommandNodeProperties implements CommandNodeProperties {

    private final boolean decimals;

    public RangeCommandNodeProperties(boolean decimals) {
        this.decimals = decimals;
    }

    public boolean isDecimals() {
        return decimals;
    }

}
