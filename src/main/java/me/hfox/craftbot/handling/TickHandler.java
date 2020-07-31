package me.hfox.craftbot.handling;

import me.hfox.craftbot.chat.ChatColour;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityRegistration;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.pathing.Tile;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayChatMessage;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayPlayerMovement;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayPlayerPosition;
import me.hfox.craftbot.world.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class TickHandler implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickHandler.class);

    private final ClientHandler clientHandler;
    private final AtomicInteger tickCounter;

    private Set<Integer> savedMissing;

    private boolean first = true;
    private Location previousTile;

    public TickHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.tickCounter = new AtomicInteger();
    }

    @Override
    public void run() {
        try {
            int tick = tickCounter.getAndIncrement();

            int currentSize = EntityRegistration.MISSING_ENTITIES.size();
            if (savedMissing != null && savedMissing.size() != currentSize) {
                LOGGER.info("Missing entities ({}): {} - found {} new", currentSize, EntityRegistration.MISSING_ENTITIES, (currentSize - savedMissing.size()));
            } else if (savedMissing != null && tick > 0 && tick % 100 == 0) {
                LOGGER.info("Missing entities ({}): {}", currentSize, EntityRegistration.MISSING_ENTITIES);
                // clientHandler.getClient().getConnection().writePacket(new PacketClientPlayChatMessage("Missing " + currentSize + " entities: " + savedMissing));

                Collection<Entity> entities = clientHandler.getWorldHandler().getWorld().getEntities().values();
                for (Entity entity : entities) {
                    if (entity instanceof Player) {
                        Player p = (Player) entity;

                        Location loc = entity.getLocation().minus(0, 1, 0);
                        LOGGER.debug("Block under {}'s feet is {} ({})", p.getName(), clientHandler.getWorldHandler().getWorld().getBlock(loc), loc);
                    }
                }
            }

            List<Tile> tiles = clientHandler.getTiles();
            if (tiles != null) {
                Location currentLocation = clientHandler.getPlayer().getLocation();
                int remainingTicks = getTicksPerTile() - (tick % getTicksPerTile());
                if (first || remainingTicks == getTicksPerTile()) {
                    Tile tile = tiles.get(0);
                    previousTile = clientHandler.getStartLocation().plus(tile.getX(), tile.getY(), tile.getZ()).plus(0, 1D, 0);
                }

                if (remainingTicks == getTicksPerTile() && !first) {
                    if (tiles.isEmpty()) {
                        clientHandler.finishedPathing();
                    } else {
                        tiles.remove(0);
                    }
                } else if (remainingTicks != getTicksPerTile()) {
                    if (tiles.isEmpty()) {
                        clientHandler.finishedPathing();
                    } else {
                        Tile tile = tiles.get(0);
                        Location nextTile = clientHandler.getStartLocation().plus(tile.getX(), tile.getY(), tile.getZ()).plus(0, 1.0, 0);

                        double xDiff = nextTile.getX() - previousTile.getX();
                        double yDiff = nextTile.getY() - previousTile.getY();
                        double zDiff = nextTile.getZ() - previousTile.getZ();

                        xDiff = (xDiff / getTicksPerTile()) * (getTicksPerTile() - remainingTicks);
                        yDiff = (yDiff / getTicksPerTile()) * (getTicksPerTile() - remainingTicks);
                        zDiff = (zDiff / getTicksPerTile()) * (getTicksPerTile() - remainingTicks);

                        if (yDiff > 0) {
                            yDiff = Math.sqrt(1 - Math.pow(yDiff - 1, 6)) + 0.1;
                        } else if (yDiff < 0) {
                            yDiff = -Math.sqrt(1 - Math.pow(Math.abs(yDiff), 8)) + 0.1;
                        }

                        // Location newLoc = new Location(xDiff, yDiff, zDiff, currentLocation.getYaw(), currentLocation.getPitch());
                        Location newLoc = previousTile.plus(xDiff, yDiff, zDiff);
                        // LOGGER.info("Current Location: {}", currentLocation);
                        // LOGGER.info("Next Tile: {}", nextTile);
                        LOGGER.info("Next Location: {}", newLoc);

                        clientHandler.getPlayer().setLocation(newLoc);
                        clientHandler.getClient().getConnection().writePacket(new PacketClientPlayPlayerPosition(newLoc.getX(), newLoc.getY(), newLoc.getZ(), true));
                    }
                }

                first = false;
            } else {
                first = true;
                clientHandler.getClient().getConnection().writePacket(new PacketClientPlayPlayerMovement(true));
            }

            savedMissing = new HashSet<>(EntityRegistration.MISSING_ENTITIES);
        } catch (Throwable ex) {
            LOGGER.error("BROKEN", ex);
        }
    }

    public static int getTicksPerTile() {
        //return 6;
        return 12;
    }

}
