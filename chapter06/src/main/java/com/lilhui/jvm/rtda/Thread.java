package com.lilhui.jvm.rtda;

import com.lilhui.jvm.rtda.heap.Method;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:20
 */
@Data
public class Thread {

    private static final int vmStackCapacity = 1024;

    private int pc;

    private VMStack stack;

    public Thread() {
        pc = 0;
        stack = new VMStack(vmStackCapacity);
    }

    public Frame newFrame(Method method) {
        return new Frame(this, method.getMaxLocals(), method.getMaxStack(), method);
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

    public boolean isStackEmpty() {
        return stack.getStack().isEmpty();
    }
}
