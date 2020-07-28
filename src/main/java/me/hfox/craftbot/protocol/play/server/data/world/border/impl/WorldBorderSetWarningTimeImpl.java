package me.hfox.craftbot.protocol.play.server.data.world.border.impl;

import me.hfox.craftbot.protocol.play.server.data.world.border.WorldBorderWarningTime;

public class WorldBorderSetWarningTimeImpl implements WorldBorderWarningTime {

    private final int warningTime;

    public WorldBorderSetWarningTimeImpl(int warningTime) {
        this.warningTime = warningTime;
    }

    @Override
    public int getWarningTime() {
        return warningTime;
    }

}
