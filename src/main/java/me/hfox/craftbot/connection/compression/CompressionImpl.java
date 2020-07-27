package me.hfox.craftbot.connection.compression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompressionImpl implements Compression {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompressionImpl.class);

    private int threshold;

    @Override
    public boolean isEnabled() {
        return threshold > 0;
    }

    @Override
    public int getThreshold() {
        return threshold;
    }

    @Override
    public void enable(int threshold) {
        if (threshold <= 0) {
            LOGGER.warn("Told to enable but passed disabled value ({})", threshold);
        }

        this.threshold = threshold;
    }

    @Override
    public void disable() {
        this.threshold = -1;
    }

}
