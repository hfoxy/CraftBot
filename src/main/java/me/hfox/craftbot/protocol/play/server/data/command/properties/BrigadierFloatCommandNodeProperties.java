package me.hfox.craftbot.protocol.play.server.data.command.properties;

import me.hfox.craftbot.protocol.play.server.data.command.CommandNodeProperties;

public class BrigadierFloatCommandNodeProperties implements CommandNodeProperties {

    private final float min;
    private final float max;

    public BrigadierFloatCommandNodeProperties(Float min, Float max) {
        this.min = min == null ? Float.MIN_VALUE : min;
        this.max = max == null ? Float.MIN_VALUE : max;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

}
