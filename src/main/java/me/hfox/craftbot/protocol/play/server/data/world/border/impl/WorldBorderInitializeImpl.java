package me.hfox.craftbot.protocol.play.server.data.world.border.impl;

import me.hfox.craftbot.protocol.play.server.data.world.border.WorldBorderInitialize;

public class WorldBorderInitializeImpl implements WorldBorderInitialize {

    private final double x;
    private final double z;
    private final double oldDiameter;
    private final double newDiameter;
    private final long speed;
    private final int portalTeleportBoundary;
    private final int warningTime;
    private final int warningBlocks;

    public WorldBorderInitializeImpl(double x, double z, double oldDiameter, double newDiameter, long speed, int portalTeleportBoundary, int warningTime, int warningBlocks) {
        this.x = x;
        this.z = z;
        this.oldDiameter = oldDiameter;
        this.newDiameter = newDiameter;
        this.speed = speed;
        this.portalTeleportBoundary = portalTeleportBoundary;
        this.warningTime = warningTime;
        this.warningBlocks = warningBlocks;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getZ() {
        return z;
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

    @Override
    public int getPortalTeleportBoundary() {
        return portalTeleportBoundary;
    }

    @Override
    public int getWarningBlocks() {
        return warningBlocks;
    }

    @Override
    public int getWarningTime() {
        return warningTime;
    }

}
