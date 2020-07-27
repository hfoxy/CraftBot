package me.hfox.craftbot.protocol.stream;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 * Buffer implementation, uses the buffer from the constructor to read/write
 *
 */
public class ProtocolBuffer extends NestedBuffer {

    public ProtocolBuffer(ByteBuf buffer) {
        super(buffer);
    }

    public int readVarInt() {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            read = readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public void writeVarInt(int value) {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }

            writeByte(temp);
        } while (value != 0);
    }

    public long readVarLong() {
        int numRead = 0;
        long result = 0;
        byte read;
        do {
            read = readByte();
            long value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 10) {
                throw new RuntimeException("VarLong is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public void writeVarLong(long value) {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }

            writeByte(temp);
        } while (value != 0);
    }

    public String readString() {
        int length = readVarInt();
        byte[] content = new byte[length];
        readBytes(content);
        return new String(content, StandardCharsets.UTF_8);
    }

    public void writeString(String string) {
        byte[] content = string.getBytes();
        writeVarInt(content.length);
        writeBytes(content);
    }

    public void writeUnsignedShort(int value) {
        short v = (short) (value & 0xFF);
        writeShort(v);
    }

}
