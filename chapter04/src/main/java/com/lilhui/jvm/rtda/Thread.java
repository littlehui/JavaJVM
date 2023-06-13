package com.lilhui.jvm.rtda;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:20
 */
public class Thread {

    private static final int vmStackCapacity = 1024;

    private int pc;

    private VMStack stack;

    public Thread() {
        pc = 0;
        stack = new VMStack(vmStackCapacity);
    }

    public Frame currentFrame() {
        return stack.top();
    }

    public void pushFrame(Frame frame) {
        stack.push(frame);
    }

    public Frame popFrame() {
        return stack.pop();
    }
}
