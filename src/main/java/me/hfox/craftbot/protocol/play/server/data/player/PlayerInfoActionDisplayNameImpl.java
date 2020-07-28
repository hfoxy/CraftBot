package me.hfox.craftbot.protocol.play.server.data.player;

import me.hfox.craftbot.chat.ChatComponent;

import java.util.Optional;
import java.util.UUID;

public class PlayerInfoActionDisplayNameImpl extends BasePlayerInfoAction implements PlayerInfoActionDisplayName {

    private final ChatComponent displayName;

    public PlayerInfoActionDisplayNameImpl(UUID uuid, ChatComponent displayName) {
        super(PlayerInfoActionType.UPDATE_DISPLAY_NAME, uuid);
        this.displayName = displayName;
    }

    @Override
    public Optional<ChatComponent> getDisplayName() {
        return Optional.ofNullable(displayName);
    }

}
