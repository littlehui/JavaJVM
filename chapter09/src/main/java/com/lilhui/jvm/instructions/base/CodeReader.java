package com.lilhui.jvm.instructions.base;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 16:33
 */
public class CodeReader {

    private byte[] code;

    private int pc;

    public CodeReader(byte[] code) {
        this.code = code;
        this.pc = 0;
    }

    public short readU1() {
        short val = (short)(code[pc] & 0xFF);
        pc++;
        return val;
    }

    public short readU2() {
        short high = (short) (readU1() & 0xFF);
        short low = (short) (readU1() & 0xFF);
        return (short) ((high << 8) | low);
    }

    public int readU4() {
        int high = readU2();
        int low = readU2();
        return (high << 16) | low;
    }

    public long readU8() {
        long high = readU4();
        long low = readU4();
        return (high << 32) | low;
    }

    public short readI1() {
        return readU1();
    }

    public short readI2() {
        return readU2();
    }

    public int readI4() {
        return readU4();
    }

    public long readI8() {
        return readU8();
    }

    public void skipPadding() {
        int skip = 4 - (pc % 4);
        if (skip != 4) {
            pc += skip;
        }
    }

    public int getPC() {
        return pc;
    }

    public void setPC(int pc) {
        this.pc = pc;
    }

    public byte[] readBytes(int length) {
        byte[] data = new byte[length];
        System.arraycopy(code, pc, data, 0, length);
        pc += length;
        return data;
    }

    public void reset(byte[] code, int pc) {
        this.code = code;
        this.pc = pc;
    }
}
