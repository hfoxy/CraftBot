package me.hfox.craftbot.protocol.play.server.data.world.border.impl;

import me.hfox.craftbot.protocol.play.server.data.world.border.WorldBorderSetSize;

public class WorldBorderSetSizeImpl implements WorldBorderSetSize {

    private final double diameter;

    public WorldBorderSetSizeImpl(double diameter) {
        this.diameter = diameter;
    }

    @Override
    public double getDiameter() {
        return diameter;
    }

}
