package me.hfox.craftbot.protocol.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

/**
 *
 * Proxy buffer, uses the constructor provided ByteBuf to perform all operations
 *
 */
public class NestedBuffer extends ByteBuf {

    private final ByteBuf buffer;

    public NestedBuffer(ByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public int capacity() {
        return buffer.capacity();
    }

    @Override
    public ByteBuf capacity(int i) {
        return buffer.capacity(i);
    }

    @Override
    public int maxCapacity() {
        return buffer.maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc() {
        return buffer.alloc();
    }

    @Override
    @Deprecated
    public ByteOrder order() {
        return buffer.order();
    }

    @Override
    @Deprecated
    public ByteBuf order(ByteOrder byteOrder) {
        return buffer.order(byteOrder);
    }

    @Override
    public ByteBuf unwrap() {
        return buffer.unwrap();
    }

    @Override
    public boolean isDirect() {
        return buffer.isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return buffer.isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly() {
        return buffer.asReadOnly();
    }

    @Override
    public int readerIndex() {
        return buffer.readerIndex();
    }

    @Override
    public ByteBuf readerIndex(int i) {
        return buffer.readerIndex(i);
    }

    @Override
    public int writerIndex() {
        return buffer.writerIndex();
    }

    @Override
    public ByteBuf writerIndex(int i) {
        return buffer.writerIndex(i);
    }

    @Override
    public ByteBuf setIndex(int i, int i1) {
        return buffer.setIndex(i, i1);
    }

    @Override
    public int readableBytes() {
        return buffer.readableBytes();
    }

    @Override
    public int writableBytes() {
        return buffer.writableBytes();
    }

    @Override
    public int maxWritableBytes() {
        return buffer.maxWritableBytes();
    }

    @Override
    public int maxFastWritableBytes() {
        return buffer.maxFastWritableBytes();
    }

    @Override
    public boolean isReadable() {
        return buffer.isReadable();
    }

    @Override
    public boolean isReadable(int i) {
        return buffer.isReadable(i);
    }

    @Override
    public boolean isWritable() {
        return buffer.isWritable();
    }

    @Override
    public boolean isWritable(int i) {
        return buffer.isWritable(i);
    }

    @Override
    public ByteBuf clear() {
        return buffer.clear();
    }

    @Override
    public ByteBuf markReaderIndex() {
        return buffer.markReaderIndex();
    }

    @Override
    public ByteBuf resetReaderIndex() {
        return buffer.resetReaderIndex();
    }

    @Override
    public ByteBuf markWriterIndex() {
        return buffer.markWriterIndex();
    }

    @Override
    public ByteBuf resetWriterIndex() {
        return buffer.resetWriterIndex();
    }

    @Override
    public ByteBuf discardReadBytes() {
        return buffer.discardReadBytes();
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        return buffer.discardSomeReadBytes();
    }

    @Override
    public ByteBuf ensureWritable(int i) {
        return buffer.ensureWritable(i);
    }

    @Override
    public int ensureWritable(int i, boolean b) {
        return buffer.ensureWritable(i, b);
    }

    @Override
    public boolean getBoolean(int i) {
        return buffer.getBoolean(i);
    }

    @Override
    public byte getByte(int i) {
        return buffer.getByte(i);
    }

    @Override
    public short getUnsignedByte(int i) {
        return buffer.getUnsignedByte(i);
    }

    @Override
    public short getShort(int i) {
        return buffer.getShort(i);
    }

    @Override
    public short getShortLE(int i) {
        return buffer.getShortLE(i);
    }

    @Override
    public int getUnsignedShort(int i) {
        return buffer.getUnsignedShort(i);
    }

    @Override
    public int getUnsignedShortLE(int i) {
        return buffer.getUnsignedShortLE(i);
    }

    @Override
    public int getMedium(int i) {
        return buffer.getMedium(i);
    }

    @Override
    public int getMediumLE(int i) {
        return buffer.getMediumLE(i);
    }

    @Override
    public int getUnsignedMedium(int i) {
        return buffer.getUnsignedMedium(i);
    }

    @Override
    public int getUnsignedMediumLE(int i) {
        return buffer.getUnsignedMediumLE(i);
    }

    @Override
    public int getInt(int i) {
        return buffer.getInt(i);
    }

    @Override
    public int getIntLE(int i) {
        return buffer.getIntLE(i);
    }

    @Override
    public long getUnsignedInt(int i) {
        return buffer.getUnsignedInt(i);
    }

    @Override
    public long getUnsignedIntLE(int i) {
        return buffer.getUnsignedIntLE(i);
    }

    @Override
    public long getLong(int i) {
        return buffer.getLong(i);
    }

    @Override
    public long getLongLE(int i) {
        return buffer.getLongLE(i);
    }

    @Override
    public char getChar(int i) {
        return buffer.getChar(i);
    }

    @Override
    public float getFloat(int i) {
        return buffer.getFloat(i);
    }

    @Override
    public float getFloatLE(int index) {
        return buffer.getFloatLE(index);
    }

    @Override
    public double getDouble(int i) {
        return buffer.getDouble(i);
    }

    @Override
    public double getDoubleLE(int index) {
        return buffer.getDoubleLE(index);
    }

    @Override
    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        return buffer.getBytes(i, byteBuf);
    }

    @Override
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1) {
        return buffer.getBytes(i, byteBuf, i1);
    }

    @Override
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1, int i2) {
        return buffer.getBytes(i, byteBuf, i1, i2);
    }

    @Override
    public ByteBuf getBytes(int i, byte[] bytes) {
        return buffer.getBytes(i, bytes);
    }

    @Override
    public ByteBuf getBytes(int i, byte[] bytes, int i1, int i2) {
        return buffer.getBytes(i, bytes, i1, i2);
    }

    @Override
    public ByteBuf getBytes(int i, ByteBuffer byteBuffer) {
        return buffer.getBytes(i, byteBuffer);
    }

    @Override
    public ByteBuf getBytes(int i, OutputStream outputStream, int i1) throws IOException {
        return buffer.getBytes(i, outputStream, i1);
    }

    @Override
    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i1) throws IOException {
        return buffer.getBytes(i, gatheringByteChannel, i1);
    }

    @Override
    public int getBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return buffer.getBytes(i, fileChannel, l, i1);
    }

    @Override
    public CharSequence getCharSequence(int i, int i1, Charset charset) {
        return buffer.getCharSequence(i, i1, charset);
    }

    @Override
    public ByteBuf setBoolean(int i, boolean b) {
        return buffer.setBoolean(i, b);
    }

    @Override
    public ByteBuf setByte(int i, int i1) {
        return buffer.setByte(i, i1);
    }

    @Override
    public ByteBuf setShort(int i, int i1) {
        return buffer.setShort(i, i1);
    }

    @Override
    public ByteBuf setShortLE(int i, int i1) {
        return buffer.setShortLE(i, i1);
    }

    @Override
    public ByteBuf setMedium(int i, int i1) {
        return buffer.setMedium(i, i1);
    }

    @Override
    public ByteBuf setMediumLE(int i, int i1) {
        return buffer.setMediumLE(i, i1);
    }

    @Override
    public ByteBuf setInt(int i, int i1) {
        return buffer.setInt(i, i1);
    }

    @Override
    public ByteBuf setIntLE(int i, int i1) {
        return buffer.setIntLE(i, i1);
    }

    @Override
    public ByteBuf setLong(int i, long l) {
        return buffer.setLong(i, l);
    }

    @Override
    public ByteBuf setLongLE(int i, long l) {
        return buffer.setLongLE(i, l);
    }

    @Override
    public ByteBuf setChar(int i, int i1) {
        return buffer.setChar(i, i1);
    }

    @Override
    public ByteBuf setFloat(int i, float v) {
        return buffer.setFloat(i, v);
    }

    @Override
    public ByteBuf setFloatLE(int index, float value) {
        return buffer.setFloatLE(index, value);
    }

    @Override
    public ByteBuf setDouble(int i, double v) {
        return buffer.setDouble(i, v);
    }

    @Override
    public ByteBuf setDoubleLE(int index, double value) {
        return buffer.setDoubleLE(index, value);
    }

    @Override
    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        return buffer.setBytes(i, byteBuf);
    }

    @Override
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1) {
        return buffer.setBytes(i, byteBuf, i1);
    }

    @Override
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1, int i2) {
        return buffer.setBytes(i, byteBuf, i1, i2);
    }

    @Override
    public ByteBuf setBytes(int i, byte[] bytes) {
        return buffer.setBytes(i, bytes);
    }

    @Override
    public ByteBuf setBytes(int i, byte[] bytes, int i1, int i2) {
        return buffer.setBytes(i, bytes, i1, i2);
    }

    @Override
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        return buffer.setBytes(i, byteBuffer);
    }

    @Override
    public int setBytes(int i, InputStream inputStream, int i1) throws IOException {
        return buffer.setBytes(i, inputStream, i1);
    }

    @Override
    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i1) throws IOException {
        return buffer.setBytes(i, scatteringByteChannel, i1);
    }

    @Override
    public int setBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return buffer.setBytes(i, fileChannel, l, i1);
    }

    @Override
    public ByteBuf setZero(int i, int i1) {
        return buffer.setZero(i, i1);
    }

    @Override
    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return buffer.setCharSequence(i, charSequence, charset);
    }

    @Override
    public boolean readBoolean() {
        return buffer.readBoolean();
    }

    @Override
    public byte readByte() {
        return buffer.readByte();
    }

    @Override
    public short readUnsignedByte() {
        return buffer.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return buffer.readShort();
    }

    @Override
    public short readShortLE() {
        return buffer.readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return buffer.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return buffer.readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return buffer.readMedium();
    }

    @Override
    public int readMediumLE() {
        return buffer.readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return buffer.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return buffer.readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return buffer.readInt();
    }

    @Override
    public int readIntLE() {
        return buffer.readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return buffer.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return buffer.readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return buffer.readLong();
    }

    @Override
    public long readLongLE() {
        return buffer.readLongLE();
    }

    @Override
    public char readChar() {
        return buffer.readChar();
    }

    @Override
    public float readFloat() {
        return buffer.readFloat();
    }

    @Override
    public float readFloatLE() {
        return buffer.readFloatLE();
    }

    @Override
    public double readDouble() {
        return buffer.readDouble();
    }

    @Override
    public double readDoubleLE() {
        return buffer.readDoubleLE();
    }

    @Override
    public ByteBuf readBytes(int i) {
        return buffer.readBytes(i);
    }

    @Override
    public ByteBuf readSlice(int i) {
        return buffer.readSlice(i);
    }

    @Override
    public ByteBuf readRetainedSlice(int i) {
        return buffer.readRetainedSlice(i);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf) {
        return buffer.readBytes(byteBuf);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        return buffer.readBytes(byteBuf, i);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i1) {
        return buffer.readBytes(byteBuf, i, i1);
    }

    @Override
    public ByteBuf readBytes(byte[] bytes) {
        return buffer.readBytes(bytes);
    }

    @Override
    public ByteBuf readBytes(byte[] bytes, int i, int i1) {
        return buffer.readBytes(bytes, i, i1);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        return buffer.readBytes(byteBuffer);
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        return buffer.readBytes(outputStream, i);
    }

    @Override
    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        return buffer.readBytes(gatheringByteChannel, i);
    }

    @Override
    public CharSequence readCharSequence(int i, Charset charset) {
        return buffer.readCharSequence(i, charset);
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return buffer.readBytes(fileChannel, l, i);
    }

    @Override
    public ByteBuf skipBytes(int i) {
        return buffer.skipBytes(i);
    }

    @Override
    public ByteBuf writeBoolean(boolean b) {
        return buffer.writeBoolean(b);
    }

    @Override
    public ByteBuf writeByte(int i) {
        return buffer.writeByte(i);
    }

    @Override
    public ByteBuf writeShort(int i) {
        return buffer.writeShort(i);
    }

    @Override
    public ByteBuf writeShortLE(int i) {
        return buffer.writeShortLE(i);
    }

    @Override
    public ByteBuf writeMedium(int i) {
        return buffer.writeMedium(i);
    }

    @Override
    public ByteBuf writeMediumLE(int i) {
        return buffer.writeMediumLE(i);
    }

    @Override
    public ByteBuf writeInt(int i) {
        return buffer.writeInt(i);
    }

    @Override
    public ByteBuf writeIntLE(int i) {
        return buffer.writeIntLE(i);
    }

    @Override
    public ByteBuf writeLong(long l) {
        return buffer.writeLong(l);
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        return buffer.writeLongLE(l);
    }

    @Override
    public ByteBuf writeChar(int i) {
        return buffer.writeChar(i);
    }

    @Override
    public ByteBuf writeFloat(float v) {
        return buffer.writeFloat(v);
    }

    @Override
    public ByteBuf writeFloatLE(float value) {
        return buffer.writeFloatLE(value);
    }

    @Override
    public ByteBuf writeDouble(double v) {
        return buffer.writeDouble(v);
    }

    @Override
    public ByteBuf writeDoubleLE(double value) {
        return buffer.writeDoubleLE(value);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        return buffer.writeBytes(byteBuf);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        return buffer.writeBytes(byteBuf, i);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i1) {
        return buffer.writeBytes(byteBuf, i, i1);
    }

    @Override
    public ByteBuf writeBytes(byte[] bytes) {
        return buffer.writeBytes(bytes);
    }

    @Override
    public ByteBuf writeBytes(byte[] bytes, int i, int i1) {
        return buffer.writeBytes(bytes, i, i1);
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer byteBuffer) {
        return buffer.writeBytes(byteBuffer);
    }

    @Override
    public int writeBytes(InputStream inputStream, int i) throws IOException {
        return buffer.writeBytes(inputStream, i);
    }

    @Override
    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        return buffer.writeBytes(scatteringByteChannel, i);
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return buffer.writeBytes(fileChannel, l, i);
    }

    @Override
    public ByteBuf writeZero(int i) {
        return buffer.writeZero(i);
    }

    @Override
    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return buffer.writeCharSequence(charSequence, charset);
    }

    @Override
    public int indexOf(int i, int i1, byte b) {
        return buffer.indexOf(i, i1, b);
    }

    @Override
    public int bytesBefore(byte b) {
        return buffer.bytesBefore(b);
    }

    @Override
    public int bytesBefore(int i, byte b) {
        return buffer.bytesBefore(i, b);
    }

    @Override
    public int bytesBefore(int i, int i1, byte b) {
        return buffer.bytesBefore(i, i1, b);
    }

    @Override
    public int forEachByte(ByteProcessor byteProcessor) {
        return buffer.forEachByte(byteProcessor);
    }

    @Override
    public int forEachByte(int i, int i1, ByteProcessor byteProcessor) {
        return buffer.forEachByte(i, i1, byteProcessor);
    }

    @Override
    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return buffer.forEachByteDesc(byteProcessor);
    }

    @Override
    public int forEachByteDesc(int i, int i1, ByteProcessor byteProcessor) {
        return buffer.forEachByteDesc(i, i1, byteProcessor);
    }

    @Override
    public ByteBuf copy() {
        return buffer.copy();
    }

    @Override
    public ByteBuf copy(int i, int i1) {
        return buffer.copy(i, i1);
    }

    @Override
    public ByteBuf slice() {
        return buffer.slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return buffer.retainedSlice();
    }

    @Override
    public ByteBuf slice(int i, int i1) {
        return buffer.slice(i, i1);
    }

    @Override
    public ByteBuf retainedSlice(int i, int i1) {
        return buffer.retainedSlice(i, i1);
    }

    @Override
    public ByteBuf duplicate() {
        return buffer.duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return buffer.retainedDuplicate();
    }

    @Override
    public int nioBufferCount() {
        return buffer.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer() {
        return buffer.nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(int i, int i1) {
        return buffer.nioBuffer(i, i1);
    }

    @Override
    public ByteBuffer internalNioBuffer(int i, int i1) {
        return buffer.internalNioBuffer(i, i1);
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return buffer.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(int i, int i1) {
        return buffer.nioBuffers(i, i1);
    }

    @Override
    public boolean hasArray() {
        return buffer.hasArray();
    }

    @Override
    public byte[] array() {
        return buffer.array();
    }

    @Override
    public int arrayOffset() {
        return buffer.arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress() {
        return buffer.hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return buffer.memoryAddress();
    }

    @Override
    public boolean isContiguous() {
        return buffer.isContiguous();
    }

    @Override
    public String toString(Charset charset) {
        return buffer.toString(charset);
    }

    @Override
    public String toString(int i, int i1, Charset charset) {
        return buffer.toString(i, i1, charset);
    }

    @Override
    public int hashCode() {
        return buffer.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return buffer.equals(o);
    }

    @Override
    public int compareTo(ByteBuf byteBuf) {
        return buffer.compareTo(byteBuf);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    public ByteBuf retain(int i) {
        return buffer.retain(i);
    }

    @Override
    public ByteBuf retain() {
        return buffer.retain();
    }

    @Override
    public ByteBuf touch() {
        return buffer.touch();
    }

    @Override
    public ByteBuf touch(Object o) {
        return buffer.touch(o);
    }

    @Override
    public int refCnt() {
        return buffer.refCnt();
    }

    @Override
    public boolean release() {
        return buffer.release();
    }

    @Override
    public boolean release(int i) {
        return buffer.release(i);
    }

}
