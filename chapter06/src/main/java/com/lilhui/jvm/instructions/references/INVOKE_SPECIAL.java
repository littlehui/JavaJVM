package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Method;
import com.lilhui.jvm.rtda.heap.constant.MethodRef;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/15 10:21
 */
public class INVOKE_SPECIAL extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        frame.getOpStack().popRef();
    }
}
