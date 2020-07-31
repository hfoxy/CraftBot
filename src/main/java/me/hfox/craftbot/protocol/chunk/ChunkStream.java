package me.hfox.craftbot.protocol.chunk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ChunkStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkStream.class);

    private final List<Long> dataArray;
    private final short bitsPerBlock;

    private int index;
    private long value;

    public ChunkStream(List<Long> dataArray, short bitsPerBlock) {
        this.dataArray = new ArrayList<>(dataArray);
        this.bitsPerBlock = bitsPerBlock;
        this.index = 64;
    }

    public short read() {
        short out = 0;

        for (int i = 0; i < bitsPerBlock; i++) {
            // finds the bit "index" which will be set to the read value
            short valIndex = (short) Math.pow(2, i);

            // increments and rolls over to next long if we have reached the last bit
            index++;
            if (index > 63) {
                value = dataArray.remove(dataArray.size() - 1);
                LOGGER.info("Reached last bit changing to next item: {}", value);
                index = 0;
            }

            // takes the least significant bit out of the current value
            // assigns that value to our read value if it is set
            if ((value & 1) == 1) {
                out |= valIndex;
            }

            // shifts the current value 1 to the right, removing the least significant bit
            LOGGER.info("[{}] Pulled {} out from {}: {} (#{} - 0x{} - {})", index, (value & 1), value, Long.toBinaryString(value), i, Integer.toHexString(valIndex), out);
            value >>>= 1;
        }

        LOGGER.info("Popping out {} ({})", out, Integer.toBinaryString(out));
        return out;
    }

    public int readableBits() {
        return dataArray.size() * 64 + (63 - index);
    }

}
