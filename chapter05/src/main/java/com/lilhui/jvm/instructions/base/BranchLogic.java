package com.lilhui.jvm.instructions.base;

import com.lilhui.jvm.rtda.Frame;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/12 14:56
 */
public class BranchLogic {

    // Helper method for branching
    public static void branch(Frame frame, int offset) {
        int pc = frame.getThread().getPc();
        int nextPC = pc + offset;
        frame.setNextPC(nextPC);
    }
}
