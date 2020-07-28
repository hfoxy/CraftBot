package me.hfox.craftbot.protocol.play.server.data.world.border.impl;

import me.hfox.craftbot.protocol.play.server.data.world.border.WorldBorderLerpSize;

public class WorldBorderLerpSizeImpl implements WorldBorderLerpSize {

    private final double oldDiameter;
    private final double newDiameter;
    private final long speed;

    public WorldBorderLerpSizeImpl(double oldDiameter, double newDiameter, long speed) {
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.speed = speed;
    }

    @Override
    public double getOldDiameter() {
        return oldDiameter;
    }

    @Override
    public double getDiameter() {
        return newDiameter;
    }

    @Override
    public long getSpeed() {
        return speed;
    }

}
