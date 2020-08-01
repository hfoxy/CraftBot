package me.hfox.craftbot.handling;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class TickHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickHandlerTest.class);

    @Test
    void movement() {
        int aX = 0;
        int bX = 1;

        int aY = 1;
        int bY = 0;

        double xDiff = bX - aX;
        double yDiff = bY - aY;
        double zDiff = 0 - 0;
        for (int i = 0; i < 12; i++) {
            double xStage = (xDiff / 12) * i;
            double yStage = (yDiff / 12) * i;
            double zStage = (zDiff / 12) * i;
            //LOGGER.info("Stage A: x={}, y={}, z={}", xStage, yStage, zStage);
            yStage = -(1 - Math.sqrt(1 - Math.pow(Math.abs(yStage), 4)));

            //LOGGER.info("Differe: x={}, y={}, z={}", xDiff, yDiff, zDiff);
            LOGGER.info("Stage B: x={}, y={}, z={}", xStage, yStage, zStage);
            // LOGGER.info("Valu: x={}, y={}, z={}", xDiff, yDiff, zDiff);
        }
    }

}