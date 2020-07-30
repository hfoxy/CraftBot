package me.hfox.craftbot.handling;

import me.hfox.craftbot.entity.EntityRegistration;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.entity.data.creation.PlayerCreationData;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.PacketServerPlaySpawnPlayer;
import me.hfox.craftbot.world.World;
import me.hfox.craftbot.world.impl.CraftWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorldHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorldHandler.class);

    private final ClientHandler clientHandler;
    private final World world;

    public WorldHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.world = new CraftWorld(clientHandler.getClient());
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
            world.addEntity(EntityRegistration.createPlayer(pcd));
            LOGGER.info("Added Player entity for '{}' - {}", info.getName(), info.getUuid());
        }
    }

}
