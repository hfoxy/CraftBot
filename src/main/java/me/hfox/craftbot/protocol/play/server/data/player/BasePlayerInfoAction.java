package me.hfox.craftbot.protocol.play.server.data.player;

import me.hfox.craftbot.utils.ToStringBuilder;

import java.util.UUID;

public class BasePlayerInfoAction implements PlayerInfoAction {

    private final PlayerInfoActionType type;
    private final UUID uuid;

    public BasePlayerInfoAction(PlayerInfoActionType type, UUID uuid) {
        this.type = type;
        this.uuid = uuid;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public PlayerInfoActionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.build(this).deepArray(true).parents(true).reflective(true).simpleName(true).toString();
    }

}
