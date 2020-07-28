package me.hfox.craftbot.protocol.play.server.data.player;

import java.util.UUID;

public interface PlayerInfoAction {

    PlayerInfoActionType getType();

    UUID getUuid();

}
