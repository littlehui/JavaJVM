package com.lilhui.jvm.rtda;

import com.lilhui.jvm.rtda.heap.Method;
import com.lilhui.jvm.rtda.heap.Object;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:23
 */
@Data
public class Frame {
    private Frame lower;
    private LocalVars localVars;
    private OPStack opStack;
    private int nextPC;
    private Thread thread;
    private Method method;

    public Frame(Thread thread, int maxLocals, int maxStack, Method method) {
        this.localVars = new LocalVars(maxLocals);
        this.opStack = new OPStack(maxStack);
        this.nextPC = 0;
        this.thread = thread;
        this.method = method;
    }

    public void revertNextPc() {
        this.nextPC = this.thread.getPc();
    }

    public void setSlot(int index, Slot slot) {
        this.localVars.setSlot(index, slot);
    }

    public Slot getSlot(int index) {
        return this.localVars.getSlot(index);
    }

    public void setRef(int index, Object ref) {
        this.localVars.setRef(index, ref);
    }

    public Object getRef(int index) {
        return this.localVars.getRef(index);
    }
}