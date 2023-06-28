package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.rtda.Slot;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 14:13
 */
@Data
public class Slots {

    private Slot[] slots;

    public Slots(int size) {
        slots = new Slot[size];
        for (int i=0; i < size; i++) {
            slots[i] = new Slot();
        }
    }

    public void setInt(int index, int value) {
        slots[index].setNum(value);
    }

    public int getInt(int index) {
        return (int) slots[index].getNum();
    }

    // 大端模式存储Long，Double
    public void setLong(int index, long value) {
        Slot high = this.slots[index];
        Slot low = this.slots[index + 1];
        high.setNum((int) (value >> 32));
        low.setNum((int) (value & 0xffffffff));
    }

    public long getLong(int index) {
        Slot high = this.slots[index];
        Slot low = this.slots[index + 1];
        long highBits = ((long) high.getNum()) << 32;
        long lowBits = ((long) low.getNum()) & 0xffffffffL;
        return highBits | lowBits;
    }

    public void setFloat(int index, float value) {
        int bits = Float.floatToIntBits(value);
        this.slots[index].setNum(bits);
    }

    public float getFloat(int index) {
        int bits = (int)this.slots[index].getNum();
        return Float.intBitsToFloat(bits);
    }

    public void setDouble(int index, double value) {
        long num = Double.doubleToLongBits(value);
        setLong(index, num);
    }

    public double getDouble(int index) {
        long num = getLong(index);
        return Double.longBitsToDouble(num);
    }

    public void setRef(int index, Object ref) {
        this.slots[index].setRef(ref);
    }

    public Object getRef(int index) {
        return this.slots[index].getRef();
    }
}
