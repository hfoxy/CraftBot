package me.hfox.craftbot.protocol.play.server.data.world.border;

public interface WorldBorderInitialize extends WorldBorderAction, WorldBorderLerpSize, WorldBorderSetCentre, WorldBorderWarningBlocks, WorldBorderWarningTime {

    int getPortalTeleportBoundary();

}
