package com.lilhui.jvm.rtda;

import lombok.Getter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:23
 */
public class Frame {

    @Getter
    private LocalVars localVars;

    @Getter
    private OPStack opStack;

    public Frame(int maxLocals, int maxStack) {
        localVars = new LocalVars(maxLocals);
        opStack = new OPStack(maxStack);
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OPStack getOpStack() {
        return opStack;
    }
}

class Slot {
    long num;
    Object ref;
}


