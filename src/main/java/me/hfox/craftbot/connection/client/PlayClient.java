package me.hfox.craftbot.connection.client;

import me.hfox.craftbot.connection.client.session.Session;
import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.handling.ClientHandler;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.login.client.PacketClientLoginStart;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginSuccess;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayClientSettings;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayClientStatus;
import me.hfox.craftbot.protocol.play.client.data.ChatMode;
import me.hfox.craftbot.protocol.play.client.data.ClientStatusAction;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayPlayerInfo;
import me.hfox.craftbot.protocol.play.server.data.player.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayClient extends BasicClient<PlayClient> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayClient.class);

    private final Map<UUID, PlayerInfo> playerInfo;

    private UUID uniqueId;
    private String name;

    private ClientHandler clientHandler;

    public PlayClient(Session session) throws BotAuthenticationFailedException {
        super(PlayClient.class, session);
        this.uniqueId = session.getUniqueId();
        this.name = session.getName();
        this.playerInfo = new HashMap<>();
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<UUID, PlayerInfo> getKnownPlayers() {
        return new HashMap<>(playerInfo);
    }

    @Override
    public void onSend(ClientPacket packet) {
        if (clientHandler != null) {
            clientHandler.onSend(packet);
        }
    }

    @Override
    public void onReceive(ServerPacket packet) {
        if (packet instanceof PacketServerLoginSuccess) {
            PacketServerLoginSuccess login = (PacketServerLoginSuccess) packet;
            uniqueId = login.getUuid();
            name = login.getUsername();
        } else if (packet instanceof PacketServerPlayPlayerInfo) {
            PacketServerPlayPlayerInfo playerInfoPacket = (PacketServerPlayPlayerInfo) packet;
            for (PlayerInfoAction action : playerInfoPacket.getPlayerActions()) {
                PlayerInfo info;
                if (action instanceof PlayerInfoActionAddPlayer) {
                    PlayerInfoActionAddPlayer addPlayer = (PlayerInfoActionAddPlayer) action;
                    if (!playerInfo.containsKey(addPlayer.getUuid())) {
                        info = new PlayerInfo(addPlayer.getUuid(), addPlayer.getName(), addPlayer.getProperties());
                        playerInfo.put(addPlayer.getUuid(), info);

                        LOGGER.debug("Received Player Info for '{}' - {}", info.getName(), info.getUuid());

                        if (addPlayer.getUuid().equals(uniqueId)) {
                            LOGGER.debug("Received Bot Player Info, sending status and settings");
                            getConnection().writePacket(new PacketClientPlayClientStatus(ClientStatusAction.RESPAWN));
                            getConnection().writePacket(new PacketClientPlayClientSettings(
                                    "en_GB", 16, ChatMode.ENABLED, true, 0xFF, Hand.RIGHT
                            ));
                        }
                    } else {
                        info = playerInfo.get(addPlayer.getUuid());
                    }
                } else if (action.getType() == PlayerInfoActionType.REMOVE_PLAYER) {
                    String name = "unknown";
                    info = playerInfo.get(action.getUuid());
                    if (info != null) {
                        name = info.getName();
                    }

                    LOGGER.debug("Removing Player Info for '{}' - {}", name, action.getUuid());
                    playerInfo.remove(action.getUuid());
                    continue;
                } else {
                    info = playerInfo.get(action.getUuid());
                }

                if (info == null) {
                    continue;
                }

                if (action instanceof PlayerInfoActionDisplayName) {
                    PlayerInfoActionDisplayName dn = (PlayerInfoActionDisplayName) action;
                    info.setDisplayName(dn.getDisplayName().orElse(null));
                    info.setDisplayNameSet(dn.getDisplayName().isPresent());
                }

                if (action instanceof PlayerInfoActionGamemode) {
                    PlayerInfoActionGamemode gm = (PlayerInfoActionGamemode) action;
                    info.setGamemode(gm.getGamemode());
                }

                if (action instanceof PlayerInfoActionLatency) {
                    PlayerInfoActionLatency ping = (PlayerInfoActionLatency) action;
                    info.setPing(ping.getPing());
                }
            }
        }

        if (clientHandler != null) {
            clientHandler.onReceive(packet);
        }
    }

    @Override
    public void completeLogin() {
        if (clientHandler != null) {
            clientHandler.stop();
        }

        clientHandler = new ClientHandler(this);
        clientHandler.start();
    }

    @Override
    protected void onConnect(String host, int port) {
        try {
            getSession().authenticate();
        } catch (BotAuthenticationFailedException ex) {
            LOGGER.error("Unable to authenticate", ex);
            getConnection().disconnect();
            return;
        }

        getConnection().writePacket(new PacketClientHandshake(578, host, port, ProtocolState.LOGIN));
        getConnection().writePacket(new PacketClientLoginStart(getSession().getName()));
    }

}
