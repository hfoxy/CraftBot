package me.hfox.craftbot.handling;

import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.entity.Entities;
import me.hfox.craftbot.entity.EntityRegistration;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.entity.data.creation.PlayerCreationData;
import me.hfox.craftbot.entity.living.ClientPlayer;
import me.hfox.craftbot.pathing.AStar;
import me.hfox.craftbot.pathing.PathingResult;
import me.hfox.craftbot.pathing.Tile;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.*;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
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

    private ClientPlayer player;
    private Future<?> tickTask;

    private Location startLocation;
    private List<Tile> tiles;

    public ClientHandler(Client client) {
        this.client = client;
        this.tickHandler = new TickHandler(this);
        this.worldHandler = new WorldHandler(this);
        this.playerInfo = new HashMap<>();
    }

    public Client getClient() {
        return client;
    }

    public TickHandler getTickHandler() {
        return tickHandler;
    }

    public WorldHandler getWorldHandler() {
        return worldHandler;
    }

    public ClientPlayer getPlayer() {
        return player;
    }

    public void start() {
        tickTask = executorService.scheduleAtFixedRate(() -> {
            Connection connection = client.getConnection();
            if (connection == null || !connection.isConnected()) {
                stop();
                return;
            }

            tickHandler.run();
        }, 50L, 50L, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        tickTask.cancel(false);
    }

    public void onSend(ClientPacket packet) {
        //
    }

    public void onReceive(ServerPacket packet) {
        if (packet instanceof PacketServerPlayJoinGame) {
            LOGGER.info("Attempting to join game");
            PacketServerPlayJoinGame joinGame = (PacketServerPlayJoinGame) packet;

            World world = worldHandler.getWorld();
            player = EntityRegistration.createClientPlayer(new PlayerCreationData(
                    world, joinGame.getEntityId(), client.getUniqueId(), playerInfo.get(client.getUniqueId())
            ));

            LOGGER.info("Joined game");
        } else if (packet instanceof PacketServerPlayPlayerPositionAndLook) {
            PacketServerPlayPlayerPositionAndLook positionAndLook = (PacketServerPlayPlayerPositionAndLook) packet;

            Location newLoc = new Location(positionAndLook.getX(), positionAndLook.getY(), positionAndLook.getZ(), positionAndLook.getYaw(), positionAndLook.getPitch());
            player.setLocation(newLoc);
        } else if (packet instanceof PacketServerPlayDisconnect) {
            stop();
        }

        worldHandler.onReceive(packet);
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public PathingResult path(Location end, int range) throws AStar.InvalidPathException {
        Location start = getPlayer().getLocation().minus(0, 1, 0);
        AStar path = new AStar(getWorldHandler().getWorld(), start, end, range);

        List<Tile> route = path.iterate();
        if (path.getPathingResult() == PathingResult.SUCCESS) {
            startLocation = start;
            tiles = route;
        }

        return path.getPathingResult();
    }

    public void finishedPathing() {
        startLocation = null;
        tiles = null;
    }

}
