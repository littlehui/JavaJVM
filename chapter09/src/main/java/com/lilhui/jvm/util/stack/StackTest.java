package com.lilhui.jvm.util.stack;

import com.lilhui.jvm.typedef.TypeDef.U4;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:36
 */
public class StackTest {
    public static void main(String[] args) {
        Stack stack = new Stack(new U4(1000));
        String s = "123";
        stack.push(s);
        String str = (String) stack.top();
        if (!str.equals(s)) {
            System.err.println("Stack error");
        }
    }
}
