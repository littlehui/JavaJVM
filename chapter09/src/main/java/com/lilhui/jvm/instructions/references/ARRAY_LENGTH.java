package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.ArrayObject;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 16:27
 */
public class ARRAY_LENGTH extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OPStack stack = frame.getOpStack();
        ArrayObject arrRef = (ArrayObject) stack.popRef();
        if (arrRef == null) {
            throw new NullPointerException();
        }
        int length = arrRef.arrayLength();
        stack.pushInt(length);
    }
}
