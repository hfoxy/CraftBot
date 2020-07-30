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
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }

    public Location(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public Location(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
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
    }

    public int getBlockY() {
        return blockY;
    }

    public void setBlockY(int blockY) {
        this.blockY = blockY;
    }

    public int getBlockZ() {
        return blockZ;
    }

    public void setBlockZ(int blockZ) {
        this.blockZ = blockZ;
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
        sb.append(", y=").append(y);
        sb.append(", z=").append(z);
        sb.append(", yaw=").append(yaw);
        sb.append(", pitch=").append(pitch);
        sb.append('}');
        return sb.toString();
    }

}
