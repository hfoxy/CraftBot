package me.hfox.craftbot.protocol.chunk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ChunkStream {

    private final long[] dataArray;
    private final short bitsPerBlock;

    private int dataIndex;

    private int index;
    private long value;

    public ChunkStream(List<Long> dataArray, short bitsPerBlock) {
        this.dataArray = new long[dataArray.size()];
        int index = 0;
        for (Long l : dataArray) {
            this.dataArray[index++] = l;
        }

        this.bitsPerBlock = bitsPerBlock;
        this.index = 64;
    }

    public short read() {
        short out = 0;

        for (int i = 0; i < bitsPerBlock; i++) {
            // finds the bit "index" which will be set to the read value
            short valIndex = (short) (1 << i);

            // increments and rolls over to next long if we have reached the last bit
            index++;
            if (index > 63) {
                value = dataArray[dataIndex++];
                index = 0;
            }

            // takes the least significant bit out of the current value
            // assigns that value to our read value if it is set
            if ((value & 1) == 1) {
                out |= valIndex;
            }

            // shifts the current value 1 to the right, removing the least significant bit
            value >>>= 1;
        }

        return out;
    }

    public int readableBits() {
        return (dataArray.length - dataIndex) * 64 + (63 - index);
    }

}
