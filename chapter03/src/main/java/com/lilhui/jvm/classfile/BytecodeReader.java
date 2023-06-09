package com.lilhui.jvm.classfile;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/26 14:04
 */
public class BytecodeReader {
    private byte[] bytecode;
    private int index;

    public BytecodeReader(byte[] bytecode) {
        this.bytecode = bytecode;
        this.index = 0;
    }

    private byte readByte() {
        return bytecode[index++];
    }

    private short readShort() {
        short high = (short) (bytecode[index++] & 0xFF);
        short low = (short) (bytecode[index++] & 0xFF);
        return (short) ((high << 8) | low);
    }

    private int readInt() {
        int byte1 = bytecode[index++] & 0xFF;
        int byte2 = bytecode[index++] & 0xFF;
        int byte3 = bytecode[index++] & 0xFF;
        int byte4 = bytecode[index++] & 0xFF;
        return (byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4;
    }

    private long readLong() {
        long high = (long) readInt() & 0xFFFFFFFFL;
        long low = (long) readInt() & 0xFFFFFFFFL;
        return (high << 32) | low;
    }



    // Public methods for reading different data types

    public byte readU1() {
        return readByte();
    }

    public short readU2() {
        return readShort();
    }

    public int readU4() {
        return readInt();
    }

    public long readU8() {
        return readLong();
    }

    public byte[] readBytes(int length) {
        byte[] data = new byte[length];
        System.arraycopy(bytecode, index, data, 0, length);
        index += length;
        return data;
    }

}
