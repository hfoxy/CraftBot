package me.hfox.craftbot.connection.compression;

public interface Compression {

    boolean isEnabled();

    int getThreshold();

    void enable(int threshold);

    void disable();

}
