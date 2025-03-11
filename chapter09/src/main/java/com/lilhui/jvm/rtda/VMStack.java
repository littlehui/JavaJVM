package com.lilhui.jvm.rtda;

import com.lilhui.jvm.stack.Stack;
import com.lilhui.jvm.typedef.TypeDef.U4;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:22
 */
@Data
public class VMStack {

    private Stack stack;

    public VMStack(int capacity) {
        stack = new Stack(new U4(capacity));
    }

    public void push(Frame frame) {
        stack.push(frame);
    }

    public Frame pop() {
        return (Frame) stack.pop();
    }

    public Frame top() {
        return (Frame) stack.top();
    }
}


