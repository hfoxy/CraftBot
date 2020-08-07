package me.hfox.craftbot.handling;

import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.entity.EntityRegistration;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.entity.data.creation.PlayerCreationData;
import me.hfox.craftbot.entity.living.ClientPlayer;
import me.hfox.craftbot.pathing.AStar;
import me.hfox.craftbot.pathing.PathingResult;
import me.hfox.craftbot.pathing.Tile;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayEntityAction;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayDisconnect;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayJoinGame;
import me.hfox.craftbot.protocol.play.server.PacketServerPlayPlayerPositionAndLook;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityAction;
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

public class ClientHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);
    private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(100);

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
        /*tickTask = EXECUTOR.scheduleWithFixedDelay(() -> {
            Connection connection = client.getConnection();
            if (connection == null || !connection.isConnected()) {
                stop();
                return;
            }

            tickHandler.run();
        }, 50L, 50L, TimeUnit.MILLISECONDS);*/
    }

    public void stop() {
        tickTask.cancel(false);
    }

    public void onSend(ClientPacket packet) {
        if (packet instanceof PacketClientPlayEntityAction) {
            PacketClientPlayEntityAction entityAction = (PacketClientPlayEntityAction) packet;
            if (entityAction.getAction() == EntityAction.START_SPRINTING || entityAction.getAction() == EntityAction.STOP_SPRINTING) {
                tickHandler.setSprinting(entityAction.getAction() == EntityAction.START_SPRINTING);
            }
        }
    }

    public void onReceive(ServerPacket packet) {
        if (packet instanceof PacketServerPlayJoinGame) {
            LOGGER.debug("Attempting to join game");
            PacketServerPlayJoinGame joinGame = (PacketServerPlayJoinGame) packet;

            World world = worldHandler.getWorld();
            player = EntityRegistration.createClientPlayer(new PlayerCreationData(
                    world, joinGame.getEntityId(), client.getUniqueId(), playerInfo.get(client.getUniqueId())
            ));
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
        AStar path;

        try {
            path = new AStar(getWorldHandler().getWorld(), start, end, range);
        } catch (AStar.InvalidPathException ex) {
            LOGGER.error("Unable to path: {}", ex.getErrorReason());
            return PathingResult.ERROR;
        }

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
