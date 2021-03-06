/*
 * By @Adamki11s
 */

package me.hfox.craftbot.pathing;

import com.google.common.collect.Lists;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;
import me.hfox.craftbot.world.palette.BlockDto;
import me.hfox.craftbot.world.palette.BlockStateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AStar {

	private static final Logger LOGGER = LoggerFactory.getLogger(AStar.class);

	private static List<String> ALLOWED_WALKABLE = Lists.newArrayList("stair");
	private static List<String> DISALLOWED_WALKABLE = Lists.newArrayList("air", "lava", "fire", "wheat", "ladder", "fence");
	private static List<String> ALLOWED_THROUGH = Lists.newArrayList("air", "door", "fence_gate", "nether_portal", "end_portal", "grass");
	private static List<String> NOT_ALLOWED_THROUGH = Lists.newArrayList("end_portal_frame", "block", "stair");

	private final int sx, sy, sz, ex, ey, ez;
	private final World w;

	private PathingResult result;

	private HashMap<String, Tile> open = new HashMap<>();
	private HashMap<String, Tile> closed = new HashMap<>();

	private void addToOpenList(Tile t, boolean modify) {
		if (open.containsKey(t.getUID())) {
			if (modify) {
				open.put(t.getUID(), t);
			}
		} else {
			open.put(t.getUID(), t);
		}
	}

	private void addToClosedList(Tile t) {
		if (!closed.containsKey(t.getUID())) {
			closed.put(t.getUID(), t);
		}
	}

	private final int range;
	private final String endUID;

	private final Location start;
	private final Location end;

	public AStar(World world, Location start, Location end, int range) throws InvalidPathException {

		boolean s = true, e = true;

		this.w = world;
		if (!(s = this.isLocationWalkable(start)) || !(e = this.isLocationWalkable(end))) {
			throw new InvalidPathException(s, e);
		}

		this.start = start;
		this.end = end;

		this.sx = start.getBlockX();
		this.sy = start.getBlockY();
		this.sz = start.getBlockZ();
		this.ex = end.getBlockX();
		this.ey = end.getBlockY();
		this.ez = end.getBlockZ();

		this.range = range;

		short sh = 0;
		Tile t = new Tile(sh, sh, sh, null);
		t.calculateBoth(sx, sy, sz, ex, ey, ez, true);
		this.open.put(t.getUID(), t);
		this.processAdjacentTiles(t);

		StringBuilder b = new StringBuilder();
		b.append(ex - sx).append(ey - sy).append(ez - sz);
		this.endUID = b.toString();
	}

	public Location getEndLocation() {
		return new Location(ex, ey, ez);
	}

	public PathingResult getPathingResult() {
		return this.result;
	}

	boolean checkOnce = false;
	
	private int abs(int i) {
		return (i < 0 ? -i : i);
	}

	public ArrayList<Tile> iterate() {

		if (!checkOnce) {
			// invert the boolean flag
			checkOnce = true;
			if((abs(sx - ex) > range) || (abs(sy - ey) > range) || (abs(sz - ez) > range)){
				this.result = PathingResult.NO_PATH;
				return null;//jump out
			}
		}
		// while not at end
		Tile current = null;

		while (canContinue()) {

			// get lowest F cost square on open list
			current = this.getLowestFTile();

			// process tiles
			this.processAdjacentTiles(current);
		}

		if (this.result != PathingResult.SUCCESS) {
			return null;
		} else {
			// path found
			LinkedList<Tile> routeTrace = new LinkedList<Tile>();
			Tile parent;

			routeTrace.add(current);

			while ((parent = current.getParent()) != null) {
				routeTrace.add(parent);
				current = parent;
			}

			Collections.reverse(routeTrace);

			return new ArrayList<>(routeTrace);
		}
	}

	private boolean canContinue() {
		// check if open list is empty, if it is no path has been found
		if (open.size() == 0) {
			this.result = PathingResult.NO_PATH;
			return false;
		} else {
			if (closed.containsKey(this.endUID)) {
				this.result = PathingResult.SUCCESS;
				return false;
			} else {
				return true;
			}
		}
	}

	private Tile getLowestFTile() {
		double f = 0;
		Tile drop = null;

		// get lowest F cost square
		for (Tile t : open.values()) {
			if (f == 0) {
				t.calculateBoth(sx, sy, sz, ex, ey, ez, true);
				f = t.getF();
				drop = t;
			} else {
				t.calculateBoth(sx, sy, sz, ex, ey, ez, true);
				double posF = t.getF();
				if (posF < f) {
					f = posF;
					drop = t;
				}
			}
		}

		// drop from open list and add to closed

		this.open.remove(drop.getUID());
		this.addToClosedList(drop);

		return drop;
	}

	private boolean isOnClosedList(Tile t) {
		return closed.containsKey(t.getUID());
	}

	// pass in the current tile as the parent
	private void processAdjacentTiles(Tile current) {

		// set of possible walk to locations adjacent to current tile
		HashSet<Tile> possible = new HashSet<Tile>(26);

		for (byte x = -1; x <= 1; x++) {
			for (byte y = -1; y <= 1; y++) {
				for (byte z = -1; z <= 1; z++) {

					if (x == 0 && y == 0 && z == 0) {
						continue;// don't check current square
					}

					Tile t = new Tile((short) (current.getX() + x), (short) (current.getY() + y), (short) (current.getZ() + z), current);

					if (!t.isInRange(this.range)) {
						// if block is out of bounds continue
						continue;
					}

					// stop trying to cut corners when they can't be cut
					int absX = Math.abs(x);
					int absZ = Math.abs(z);
					if (absX == 1 && absZ == 1) {
						Location cutX = start.plus(current.getX(), current.getY(), current.getZ()).plus(0, y, z);
						Location cutZ = start.plus(current.getX(), current.getY(), current.getZ()).plus(x, y, 0);

						if (!isLocationWalkable(cutX)) {
							LOGGER.debug("unable to cut X corner @ x={}, y={}, z={}", cutX.getBlockX(), cutX.getBlockY(), cutX.getBlockZ());
							continue;
						} else if (!isLocationWalkable(cutZ)) {
							LOGGER.debug("unable to cut Z corner @ x={}, y={}, z={}", cutZ.getBlockX(), cutZ.getBlockY(), cutZ.getBlockZ());
							continue;
						}
					}

					if (x != 0 && z != 0 && (y == 0 || y == 1)) {
						// check to stop jumping through diagonal blocks
						Tile xOff = new Tile((short) (current.getX() + x), (short) (current.getY() + y), current.getZ(), current), zOff = new Tile(current.getX(),
								(short) (current.getY() + y), (short) (current.getZ() + z), current);
						if (!this.isTileWalkable(xOff) && !this.isTileWalkable(zOff)) {
							continue;
						}
					}

					if (this.isOnClosedList(t)) {
						// ignore tile
						continue;
					}

					// only process the tile if it can be walked on
					if (this.isTileWalkable(t)) {
						t.calculateBoth(sx, sy, sz, ex, ey, ez, true);
						possible.add(t);
					}

				}
			}
		}

		for (Tile t : possible) {
			// get the reference of the object in the array
			Tile openRef = null;
			if ((openRef = this.isOnOpenList(t)) == null) {
				// not on open list, so add
				this.addToOpenList(t, false);
			} else {
				// is on open list, check if path to that square is better using
				// G cost
				if (t.getG() < openRef.getG()) {
					// if current path is better, change parent
					openRef.setParent(current);
					// force updates of F, G and H values.
					openRef.calculateBoth(sx, sy, sz, ex, ey, ez, true);
				}

			}
		}

	}

	private Tile isOnOpenList(Tile t) {
		return open.getOrDefault(t.getUID(), null);
	}

	private boolean isTileWalkable(Tile t) {
		return isLocationWalkable(new Location((sx + t.getX()), (sy + t.getY()), (sz + t.getZ())));
	}

	private boolean isLocationWalkable(Location l) {
		BlockStateDto blockState = w.getBlock(l);
		BlockDto blockInfo = blockState.getBlock();
		String identifier = blockInfo.getIdentifier();

		boolean ignoredDisallowed = false;
		for (String block : ALLOWED_WALKABLE) {
			if (identifier.contains(block)) {
				ignoredDisallowed = true;
				break;
			}
		}

		if (!ignoredDisallowed) {
			for (String block : DISALLOWED_WALKABLE) {
				if (identifier.contains(block)) {
					LOGGER.debug("Block is not walkable: {}", identifier);
					return false;
				}
			}
		}

		if (identifier.contains("fence_gate") || identifier.contains("door")) {
			LOGGER.debug("Gate/door: {}", blockState.getProperties().get("open"));
			return blockState.getProperties().get("open").equals("true");
		}

		Location up1 = l.plus(0, 1, 0);
		BlockStateDto blockUp1 = w.getBlock(up1);
		Location up2 = l.plus(0, 2, 0);
		BlockStateDto blockUp2 = w.getBlock(up2);

		LOGGER.debug("Block at {} is {}", up1, blockUp1.getBlock().getIdentifier());
		LOGGER.debug("Block at {} is {}", up2, blockUp2.getBlock().getIdentifier());
		return canBlockBeWalkedThrough(blockUp1.getBlock().getIdentifier()) && canBlockBeWalkedThrough(blockUp2.getBlock().getIdentifier());
	}

	private boolean canBlockBeWalkedThrough(String identifier) {
		if (identifier.equalsIgnoreCase("minecraft:oak_stairs")) {
			LOGGER.info("Checking if oak_stairs can be walked through");
		}

		for (String blk : NOT_ALLOWED_THROUGH) {
			if (identifier.contains(blk)) {
				return false;
			}
		}

		for (String blk : ALLOWED_THROUGH) {
			if (identifier.contains(blk)) {
				if (identifier.equalsIgnoreCase("minecraft:oak_stairs")) {
					LOGGER.info("yes they can!");
				}

				return true;
			}
		}

		LOGGER.debug("Not allowed through block: {}", identifier);
		return false;
	}

	@SuppressWarnings("serial")
	public static class InvalidPathException extends Exception {

		private final boolean s, e;

		public InvalidPathException(boolean s, boolean e) {
			this.s = s;
			this.e = e;
		}

		public String getErrorReason() {
			StringBuilder sb = new StringBuilder();
			if (!s) {
				sb.append("Start Location was air. ");
			}
			if (!e) {
				sb.append("End Location was air.");
			}
			return sb.toString();
		}

		public boolean isStartNotSolid() {
			return (!s);
		}

		public boolean isEndNotSolid() {
			return (!e);
		}
	}

}
