package me.hfox.craftbot.protocol.play.server.data.world.border.impl;

import me.hfox.craftbot.protocol.play.server.data.world.border.WorldBorderWarningBlocks;

public class WorldBorderSetWarningBlocksImpl implements WorldBorderWarningBlocks {

    private final int warningBlocks;

    public WorldBorderSetWarningBlocksImpl(int warningBlocks) {
        this.warningBlocks = warningBlocks;
    }

    @Override
    public int getWarningBlocks() {
        return warningBlocks;
    }

}
