package com.lilhui.jvm.rtda;

import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:47
 */
@Data
public class LocalVars {

    private Slot[] slots;

    public LocalVars(int maxLocals) {
        slots = new Slot[maxLocals];
        for (int i = 0; i < maxLocals; i++) {
            slots[i] = new Slot();
        }
    }

    public void setInt(int index, int val) {
        slots[index].num = (long) val;
    }

    public int getInt(int index) {
        return (int) slots[index].num;
    }

    public void setLong(int index, long val) {
        slots[index].num = (val >> 32) & 0xFFFFFFFFL;
        slots[index + 1].num = val & 0xFFFFFFFFL;
    }

    public long getLong(int index) {
        long high = slots[index].num;
        long low = slots[index + 1].num;
        return (high << 32) | (low & 0xFFFFFFFFL);
    }

    public void setFloat(int index, float val) {
        int bits = Float.floatToIntBits(val);
        slots[index].num = (long) bits;
    }

    public float getFloat(int index) {
        int bits = (int) slots[index].num;
        return Float.intBitsToFloat(bits);
    }

    public void setDouble(int index, double val) {
        long bits = Double.doubleToLongBits(val);
        setLong(index, bits);
    }

    public double getDouble(int index) {
        long bits = getLong(index);
        return Double.longBitsToDouble(bits);
    }

    public void setRef(int index, Object ref) {
        slots[index].ref = ref;
    }

    public Object getRef(int index) {
        return slots[index].ref;
    }
}
