package me.hfox.craftbot.handling;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityRegistration;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.pathing.Tile;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayPlayerMovement;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayPlayerPosition;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayPlayerPositionAndRotation;
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

    private double[] xPositiveDiffLookup;
    private double[] xNegativeDiffLookup;
    private double[] yPositiveDiffLookup;
    private double[] yNegativeDiffLookup;
    private double[] zPositiveDiffLookup;
    private double[] zNegativeDiffLookup;

    public TickHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.tickCounter = new AtomicInteger();

        recalculateDiffLookupTables();
    }

    public void recalculateDiffLookupTables() {
        this.xPositiveDiffLookup = new double[getTicksPerTile()];
        this.xNegativeDiffLookup = new double[getTicksPerTile()];
        this.yPositiveDiffLookup = new double[getTicksPerTile()];
        this.yNegativeDiffLookup = new double[getTicksPerTile()];
        this.zPositiveDiffLookup = new double[getTicksPerTile()];
        this.zNegativeDiffLookup = new double[getTicksPerTile()];

        int halfTileTicks = getTicksPerHalfTile();
        double partSize = 1D / getTicksPerTile();
        double negPartSize = -1D / getTicksPerTile();
        for (int i = 0; i < getTicksPerTile(); i++) {
            double diff = partSize * i;

            if (i < halfTileTicks) {
                yNegativeDiffLookup[i] = 0;
            } else {
                yNegativeDiffLookup[i] = -(1 - Math.sqrt(1 - Math.pow(Math.abs(negPartSize), 2))) + 0.05;
            }

            yPositiveDiffLookup[i] = Math.sqrt(1 - Math.pow(diff - 1, 6)) + 0.1;

            // double positiveSoftCurve = Math.sqrt(1 - Math.pow(partSize - 1, 10));
            // double negativeSoftCurve = -(1 - Math.sqrt(1 - Math.pow(Math.abs(negPartSize), 10)));
            // xPositiveDiffLookup[i] = positiveSoftCurve;
            // xNegativeDiffLookup[i] = negativeSoftCurve;
            // zPositiveDiffLookup[i] = positiveSoftCurve;
            // zNegativeDiffLookup[i] = negativeSoftCurve;

            xPositiveDiffLookup[i] = diff;
            xNegativeDiffLookup[i] = -diff;
            zPositiveDiffLookup[i] = diff;
            zNegativeDiffLookup[i] = -diff;
        }
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
                        Tile lastTile = tiles.remove(0);
                        clientHandler.getClient().getConnection().writePacket(new PacketClientPlayPlayerMovement(true));

                        if (tiles.isEmpty()) {
                            Location finalTile = clientHandler.getStartLocation().plus(lastTile.getX(), lastTile.getY(), lastTile.getZ()).plus(0, 1.0, 0);
                            clientHandler.getPlayer().setLocation(finalTile);
                            clientHandler.getClient().getConnection().writePacket(new PacketClientPlayPlayerPosition(finalTile.getX(), finalTile.getY(), finalTile.getZ(), true));
                            clientHandler.finishedPathing();
                        }
                    }
                } else if (remainingTicks != getTicksPerTile()) {
                    if (tiles.isEmpty()) {
                        clientHandler.finishedPathing();
                    } else {
                        Float yaw = null;
                        Float pitch = null;

                        Tile tile = tiles.get(0);
                        Location nextTile = clientHandler.getStartLocation().plus(tile.getX(), tile.getY(), tile.getZ()).plus(0, 1.0, 0);
                        if (tiles.size() > 2) {
                            Tile followingTile = tiles.get(0);

                            Location followingTileLoc = clientHandler.getStartLocation().plus(followingTile.getX(), followingTile.getY(), followingTile.getZ()).plus(0, 1.0, 0);
                            Location nextDiff = followingTileLoc.minus(nextTile);

                            nextTile = nextTile.plus(nextDiff.getX() * 0.4, 0, nextDiff.getY() * 0.4);
                        }

                        int i = tick % getTicksPerTile();

                        double xDiff = nextTile.getX() - previousTile.getX();
                        double yDiff = nextTile.getY() - previousTile.getY();
                        double zDiff = nextTile.getZ() - previousTile.getZ();

                        // x = 90 | -x = -90
                        // z = 0  | -z = 180
                        // y = 40 | -y = -40
                        if (xDiff > 0 && zDiff > 0) {
                            yaw = -45F;
                        } else if (xDiff < 0 && zDiff < 0) {
                            yaw = 135F;
                        } else if (xDiff > 0 && zDiff < 0) {
                            yaw = -135F;
                        } else if (xDiff < 0 && zDiff > 0) {
                            yaw = 45F;
                        } else if (xDiff > 0) {
                            yaw = -90F;
                        } else if (xDiff < 0) {
                            yaw = 90F;
                        } else if (zDiff > 0) {
                            yaw = 0F;
                        } else if (zDiff < 0) {
                            yaw = 180F;
                        }

                        if (yDiff > 0) {
                            pitch = -40F;
                        } else if (yDiff < 0) {
                            pitch = 40F;
                        } else if (yDiff == 0) {
                            pitch = 0F;
                        }

                        boolean grounded = yDiff == 0;

                        xDiff = xDiff == 0 ? 0 : xDiff > 0 ? xPositiveDiffLookup[i] : xNegativeDiffLookup[i];
                        yDiff = yDiff == 0 ? 0 : yDiff > 0 ? yPositiveDiffLookup[i] : yNegativeDiffLookup[i];
                        zDiff = zDiff == 0 ? 0 : zDiff > 0 ? zPositiveDiffLookup[i] : zNegativeDiffLookup[i];

                        // Location newLoc = new Location(xDiff, yDiff, zDiff, currentLocation.getYaw(), currentLocation.getPitch());
                        Location newLoc = previousTile.plus(xDiff, yDiff, zDiff);
                        if (pitch != null) {
                            newLoc.setPitch(pitch);
                        } else {
                            newLoc.setPitch(currentLocation.getPitch());
                        }

                        if (yaw != null) {
                            newLoc.setYaw(yaw);
                        } else {
                            newLoc.setYaw(currentLocation.getYaw());
                        }

                        // LOGGER.info("Current Location: {}", currentLocation);
                        // LOGGER.info("Next Tile: {}", nextTile);
                        // LOGGER.info("Next Location: y={}", String.format("%.2f", newLoc.getY()));

                        clientHandler.getPlayer().setLocation(newLoc);
                        clientHandler.getClient().getConnection().writePacket(new PacketClientPlayPlayerPositionAndRotation(newLoc.getX(), newLoc.getY(), newLoc.getZ(), newLoc.getYaw(), newLoc.getPitch(), grounded));

                        if ((tick % getTicksPerTile()) == (getTicksPerTile() - 1)) {
                            previousTile = nextTile;
                        }
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
        return 6;
    }

    public static int getTicksPerHalfTile() {
        return getTicksPerTile() / 2;
    }

}
