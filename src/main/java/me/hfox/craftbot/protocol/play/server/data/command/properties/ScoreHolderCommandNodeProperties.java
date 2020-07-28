package me.hfox.craftbot.protocol.play.server.data.command.properties;

import me.hfox.craftbot.protocol.play.server.data.command.CommandNodeProperties;

public class ScoreHolderCommandNodeProperties implements CommandNodeProperties {

    private final boolean multiple;

    public ScoreHolderCommandNodeProperties(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isMultiple() {
        return multiple;
    }

}
