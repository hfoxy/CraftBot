package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.player.Gamemode;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.player.*;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;
import java.util.UUID;

public class PacketServerPlayPlayerInfo implements ServerPacket {

    private PlayerInfoActionType actionType;
    private PlayerInfoAction[] playerActions;

    public PlayerInfoActionType getActionType() {
        return actionType;
    }

    public PlayerInfoAction[] getPlayerActions() {
        return playerActions;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        actionType = PlayerInfoActionType.values()[buffer.readVarInt()];

        playerActions = new PlayerInfoAction[buffer.readVarInt()];
        for (int i = 0; i < playerActions.length; i++) {
            UUID uuid = buffer.readUuid();
            Gamemode gamemode;
            int ping;
            ChatComponent displayName;

            PlayerInfoAction action;
            switch (actionType) {
                case ADD_PLAYER:
                    String name = buffer.readString();
                    PlayerInfoProperty[] properties = new PlayerInfoProperty[buffer.readVarInt()];
                    for (int j = 0; j < properties.length; j++) {
                        properties[j] = buffer.readPlayerInfoProperty();
                    }

                    gamemode = Gamemode.findById(buffer.readVarInt());
                    ping = buffer.readVarInt();
                    displayName = buffer.readBoolean() ? buffer.readChat() : null;

                    action = new PlayerInfoActionAddPlayerImpl(uuid, name, properties, gamemode, ping, displayName);
                    break;
                case UPDATE_GAMEMODE:
                    gamemode = Gamemode.findById(buffer.readVarInt());
                    action = new PlayerInfoActionGamemodeImpl(uuid, gamemode);
                    break;
                case UPDATE_LATENCY:
                    ping = buffer.readVarInt();
                    action = new PlayerInfoActionLatencyImpl(uuid, ping);
                    break;
                case UPDATE_DISPLAY_NAME:
                    displayName = buffer.readBoolean() ? buffer.readChat() : null;
                    action = new PlayerInfoActionDisplayNameImpl(uuid, displayName);
                    break;
                default:
                    action = new BasePlayerInfoAction(actionType, uuid);
                    break;
            }

            playerActions[i] = action;
        }
    }

}
