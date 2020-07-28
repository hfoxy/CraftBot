package me.hfox.craftbot.protocol.play.server.data.command.properties;

import me.hfox.craftbot.protocol.play.server.data.command.CommandNodeProperties;

public class BrigadierIntegerCommandNodeProperties implements CommandNodeProperties {

    private final int min;
    private final int max;

    public BrigadierIntegerCommandNodeProperties(Integer min, Integer max) {
        this.min = min == null ? Integer.MIN_VALUE : min;
        this.max = max == null ? Integer.MIN_VALUE : max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

}
