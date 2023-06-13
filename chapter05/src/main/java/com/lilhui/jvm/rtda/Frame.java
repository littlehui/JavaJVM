package com.lilhui.jvm.rtda;

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

    public Frame(Thread thread, int maxLocals, int maxStack) {
        this.localVars = new LocalVars(maxLocals);
        this.opStack = new OPStack(maxStack);
        this.nextPC = 0;
        this.thread = thread;
    }
}