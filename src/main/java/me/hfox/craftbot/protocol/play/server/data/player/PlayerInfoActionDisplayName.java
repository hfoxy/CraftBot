package me.hfox.craftbot.protocol.play.server.data.player;

import me.hfox.craftbot.chat.ChatComponent;

import java.util.Optional;

public interface PlayerInfoActionDisplayName extends PlayerInfoAction {

    Optional<ChatComponent> getDisplayName();

}
