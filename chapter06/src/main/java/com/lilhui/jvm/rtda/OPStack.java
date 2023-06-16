package com.lilhui.jvm.rtda;

import com.lilhui.jvm.rtda.heap.Object;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:46
 */
@Data
public class OPStack {
    private Slot[] slots;
    private int size;

    public OPStack(int capacity) {
        slots = new Slot[capacity];
        for (int i = 0; i < capacity; i++) {
            slots[i] = new Slot();
        }
        size = 0;
    }

    private void expand() {
        int capacity = slots.length;
        capacity = capacity * 2;
        Slot[] newSlots = new Slot[capacity];
        for (int i = 0; i < capacity; i++) {
            if (i < slots.length) {
                newSlots[i] = slots[i];
            } else {
                newSlots[i] = new Slot();
            }
        }
        slots = newSlots;
    }

    public void pushInt(int val) {
        int index = size;
        size++;
        slots[index].num = (long) val;
    }

    public int popInt() {
        int index = size - 1;
        size--;
        long val = slots[index].num;
        return (int) val;
    }

    public void pushLong(long val) {
        int index = size;
        size++;
        long u64 = val;
        int high = (int) (u64 >> 32);
        int low = (int) u64;
        slots[index].num = (long) high;

        index = size;
        size++;
        if (slots.length < size) {
            expand();
        }
        slots[index].num = (long) low;
    }

    public long popLong() {
        size--;
        int index = size;
        int low = (int) slots[index].num;
        size--;
        index = size;
        int high = (int) slots[index].num;
        long val = ((long) high << 32) | (low & 0xFFFFFFFFL);
        return val;
    }

    public void pushFloat(float val) {
        int bits = Float.floatToIntBits(val);
        int index = size;
        size++;
        slots[index].num = (long) bits;
    }

    public float popFloat() {
        size--;
        int index = size;
        int bits = (int) slots[index].num;
        return Float.intBitsToFloat(bits);
    }

    public void pushDouble(double val) {
        long bits = Double.doubleToLongBits(val);
        pushLong(bits);
    }

    public double popDouble() {
        long bits = popLong();
        return Double.longBitsToDouble(bits);
    }

    public void pushRef(Object ref) {
        int index = size;
        size++;
        slots[index].ref = ref;
    }

    public Object popRef() {
        size--;
        int index = size;
        return slots[index].ref;
    }

    public void pushSlot(Slot slot) {
        int index = size;
        size++;
        slots[index] = slot;
    }

    public Slot popSlot() {
        size--;
        int index = size;
        return slots[index];
    }
}