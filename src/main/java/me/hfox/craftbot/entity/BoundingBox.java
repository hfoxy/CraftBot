package me.hfox.craftbot.entity;

public class BoundingBox {

    private final double width;
    private final double depth;
    private final double height;

    public BoundingBox(double width, double depth, double height) {
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getDepth() {
        return depth;
    }

    public double getHeight() {
        return height;
    }

}
