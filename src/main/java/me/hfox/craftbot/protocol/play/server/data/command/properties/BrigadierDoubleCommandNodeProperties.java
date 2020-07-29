package me.hfox.craftbot.protocol.play.server.data.command.properties;

import me.hfox.craftbot.protocol.play.server.data.command.CommandNodeProperties;

public class BrigadierDoubleCommandNodeProperties implements CommandNodeProperties {

    private final double min;
    private final double max;

    public BrigadierDoubleCommandNodeProperties(Double min, Double max) {
        this.min = min == null ? Double.MIN_VALUE : min;
        this.max = max == null ? Double.MIN_VALUE : max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

}
