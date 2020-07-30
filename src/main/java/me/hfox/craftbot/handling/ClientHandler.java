package me.hfox.craftbot.handling;

import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayPlayerInfo;
import me.hfox.craftbot.protocol.play.server.PacketServerPlaySpawnEntity;
import me.hfox.craftbot.protocol.play.server.PacketServerPlaySpawnLivingEntity;
import me.hfox.craftbot.protocol.play.server.PacketServerPlaySpawnPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    private final Client client;

    private final TickHandler tickHandler;
    private final WorldHandler worldHandler;

    private final Map<UUID, PlayerInfo> playerInfo;

    private Future<?> tickTask;

    public ClientHandler(Client client) {
        this.client = client;
        this.tickHandler = new TickHandler(this);
        this.worldHandler = new WorldHandler(this);
        this.playerInfo = new HashMap<>();
    }

    public Client getClient() {
        return client;
    }

    public WorldHandler getWorldHandler() {
        return worldHandler;
    }

    public void start() {
        tickTask = executorService.scheduleAtFixedRate(() -> {
            Connection connection = client.getConnection();
            if (connection == null || !connection.isConnected()) {
                stop();
                return;
            }

            tickHandler.run();
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        tickTask.cancel(false);
    }

    public void onSend(ClientPacket packet) {
        //
    }

    public void onReceive(ServerPacket packet) {
        if (packet instanceof PacketServerPlaySpawnPlayer
                || packet instanceof PacketServerPlaySpawnEntity
                || packet instanceof PacketServerPlaySpawnLivingEntity
                || packet instanceof PacketServerPlayPlayerInfo) {
            worldHandler.onReceive(packet);
        }
    }

}
