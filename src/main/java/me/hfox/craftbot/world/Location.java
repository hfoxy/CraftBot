package me.hfox.craftbot.world;

public class Location {

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    private int blockX;
    private int blockY;
    private int blockZ;

    private Location() {
        // for cloning purposes
    }

    public Location(int blockX, int blockY, int blockZ) {
        setBlockX(blockX);
        setBlockY(blockY);
        setBlockZ(blockZ);
    }

    public Location(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public Location(double x, double y, double z, float yaw, float pitch) {
        setX(x);
        setY(y);
        setZ(z);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        this.blockX = (int) Math.floor(x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        this.blockY = (int) Math.floor(y);
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        this.blockZ = (int) Math.floor(z);
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public int getBlockX() {
        return blockX;
    }

    public void setBlockX(int blockX) {
        this.blockX = blockX;
        this.x = blockX + 0.5D;
    }

    public int getChunkX() {
        return (int) Math.floor((double) getBlockX() / 16);
    }

    public int getChunkBlockX() {
        int chunkX = getChunkX();
        if (chunkX < 0) {
            return getBlockX() - (chunkX * 16);
        } else {
            return getBlockX() % 16;
        }
    }

    public int getBlockY() {
        return blockY;
    }

    public void setBlockY(int blockY) {
        this.blockY = blockY;
        this.y = blockY;
    }

    public int getBlockZ() {
        return blockZ;
    }

    public void setBlockZ(int blockZ) {
        this.blockZ = blockZ;
        this.z = blockZ + 0.5D;
    }

    public int getChunkZ() {
        return (int) Math.floor((double) getBlockZ() / 16);
    }

    public int getChunkBlockZ() {
        int chunkZ = getChunkZ();
        if (chunkZ < 0) {
            return getBlockZ() - (chunkZ * 16);
        } else {
            return getBlockZ() % 16;
        }
    }

    public Location plus(Location location) {
        return plus(location.getX(), location.getY(), location.getZ());
    }

    public Location plus(double x, double y, double z) {
        return new Location(getX() + x, getY() + y, getZ() + z);
    }

    public Location plus(int x, int y, int z) {
        return new Location(getBlockX() + x, getBlockY() + y, getBlockZ() + z);
    }

    public Location minus(Location location) {
        return minus(location.getX(), location.getY(), location.getZ());
    }

    public Location minus(double x, double y, double z) {
        return new Location(getX() - x, getY() - y, getZ() - z);
    }

    public Location minus(int x, int y, int z) {
        return new Location(getBlockX() - x, getBlockY() - y, getBlockZ() - z);
    }

    public Location copy() {
        Location loc = new Location();
        loc.x = x;
        loc.blockX = blockX;
        loc.y = y;
        loc.blockY = blockY;
        loc.z = z;
        loc.blockZ = blockZ;
        loc.yaw = yaw;
        loc.pitch = pitch;
        return loc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("x=").append(x);
        sb.append(", blockX=").append(blockX);
        sb.append(", y=").append(y);
        sb.append(", blockY=").append(blockY);
        sb.append(", z=").append(z);
        sb.append(", blockZ=").append(blockZ);
        sb.append(", yaw=").append(yaw);
        sb.append(", pitch=").append(pitch);
        sb.append('}');
        return sb.toString();
    }

}
