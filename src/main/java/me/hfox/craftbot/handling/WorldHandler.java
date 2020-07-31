package me.hfox.craftbot.handling;

import me.hfox.craftbot.entity.Entities;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityRegistration;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.entity.data.creation.EntityCreationData;
import me.hfox.craftbot.entity.data.creation.PlayerCreationData;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayChatMessage;
import me.hfox.craftbot.protocol.play.server.*;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;
import me.hfox.craftbot.world.impl.CraftWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class WorldHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldHandler.class);

    private final ClientHandler clientHandler;
    private final World world;
    private final ChunkHandler chunkHandler;

    public WorldHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.world = new CraftWorld(clientHandler.getClient());
        this.chunkHandler = new ChunkHandler(this);
        Entities.load();
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public World getWorld() {
        return world;
    }

    public void onReceive(ServerPacket packet) {
        if (packet instanceof PacketServerPlaySpawnPlayer) {
            PacketServerPlaySpawnPlayer spawn = (PacketServerPlaySpawnPlayer) packet;
            PlayerInfo info = clientHandler.getClient().getKnownPlayers().get(spawn.getUuid());
            PlayerCreationData pcd = new PlayerCreationData(world, spawn.getEntityId(), spawn.getUuid(), info);

            Player player = EntityRegistration.createPlayer(pcd);
            Location location = new Location(spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch());
            player.setLocation(location);

            world.addEntity(player);
            LOGGER.debug("Added Player entity for '{}' - {}", info.getName(), info.getUuid());

            clientHandler.getClient().getConnection().writePacket(new PacketClientPlayChatMessage("Hello " + player.getName()));
        } else if (packet instanceof PacketServerPlaySpawnEntity) {
            PacketServerPlaySpawnEntity spawn = (PacketServerPlaySpawnEntity) packet;
            EntityCreationData ecd = new EntityCreationData(world, spawn.getId(), spawn.getUuid());
            LOGGER.debug("Request spawn of {} ({} - {})", spawn.getType(), spawn.getId(), spawn.getUuid());

            Entity entity = EntityRegistration.createEntity(spawn.getType(), ecd);
            Location location = new Location(spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch());
            entity.setLocation(location);

            world.addEntity(entity);
            LOGGER.debug("Added {} entity", entity.getClass().getSimpleName());
        } else if (packet instanceof PacketServerPlayDestroyEntities) {
            List<Entity> removed = world.removeEntitiesById(((PacketServerPlayDestroyEntities) packet).getEntityIds());
            LOGGER.debug("Removed {} entities", removed);

            for (Entity rem : removed) {
                if (rem instanceof Player) {
                    Player p = (Player) rem;
                    clientHandler.getClient().getConnection().writePacket(new PacketClientPlayChatMessage("Bye " + p.getName()));
                }
            }
        } else if (packet instanceof PacketServerPlayEntityMetadata) {
            PacketServerPlayEntityMetadata entityMetadata = (PacketServerPlayEntityMetadata) packet;

            world.findEntityById(entityMetadata.getEntityId()).ifPresent((entity) -> {
                for (EntityMetadata metadata : entityMetadata.getMetadata()) {
                    try {
                        entity.readMetadata(metadata);
                    } catch (IOException ex) {
                        throw new BotUnsupportedEntityException("Unable to read metadata", ex);
                    }
                }
            });
        } else if (packet instanceof PacketServerPlayEntityPositionAndRotation) {
            PacketServerPlayEntityPositionAndRotation movement = (PacketServerPlayEntityPositionAndRotation) packet;

            world.findEntityById(movement.getEntityId()).ifPresent((entity) -> {
                // LOGGER.debug("[MOVEMENT] Position and Rotation: '{}'", entity.getBriefInfo());

                Location location = entity.getLocation();
                location.setX(location.getX() + movement.getDeltaX());
                location.setY(location.getY() + movement.getDeltaY());
                location.setZ(location.getZ() + movement.getDeltaZ());
                location.setYaw(movement.getYaw());
                location.setPitch(movement.getPitch());
                entity.setLocation(location);
            });
        } else if (packet instanceof PacketServerPlayEntityPosition) {
            PacketServerPlayEntityPosition movement = (PacketServerPlayEntityPosition) packet;

            world.findEntityById(movement.getEntityId()).ifPresent((entity) -> {
                // LOGGER.debug("[MOVEMENT] Position: '{}'", entity.getBriefInfo());

                Location location = entity.getLocation();
                location.setX(location.getX() + movement.getDeltaX());
                location.setY(location.getY() + movement.getDeltaY());
                location.setZ(location.getZ() + movement.getDeltaZ());
                entity.setLocation(location);
            });
        } else if (packet instanceof PacketServerPlayEntityRotation) {
            PacketServerPlayEntityRotation movement = (PacketServerPlayEntityRotation) packet;

            world.findEntityById(movement.getEntityId()).ifPresent((entity) -> {
                // LOGGER.debug("[MOVEMENT] Rotation: '{}'", entity.getBriefInfo());

                Location location = entity.getLocation();
                location.setYaw(movement.getYaw());
                location.setPitch(movement.getPitch());
                entity.setLocation(location);
            });
        } else if (packet instanceof PacketServerPlayEntityTeleport) {
            PacketServerPlayEntityTeleport movement = (PacketServerPlayEntityTeleport) packet;

            world.findEntityById(movement.getEntityId()).ifPresent((entity) -> {
                // LOGGER.debug("[MOVEMENT] Teleport: '{}'", entity.getBriefInfo());

                Location location = entity.getLocation();
                location.setX(movement.getX());
                location.setY(movement.getY());
                location.setZ(movement.getZ());
                location.setYaw(movement.getYaw());
                location.setPitch(movement.getPitch());
                entity.setLocation(location);
            });
        }

        chunkHandler.onReceive(packet);
    }

}
