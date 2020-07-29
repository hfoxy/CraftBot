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

    public Location(int blockX, int blockY, int blockZ) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }

}
