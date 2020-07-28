package me.hfox.craftbot.protocol.play.server.data.world.border.impl;

import me.hfox.craftbot.protocol.play.server.data.world.border.WorldBorderSetCentre;

public class WorldBorderSetCentreImpl implements WorldBorderSetCentre {

    private final double x;
    private final double z;

    public WorldBorderSetCentreImpl(double x, double z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getZ() {
        return z;
    }

}
