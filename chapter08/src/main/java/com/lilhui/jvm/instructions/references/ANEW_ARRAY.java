package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.ClazzHelper;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.constant.ClazzRef;
import com.lilhui.jvm.rtda.heap.Object;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 14:50
 */
public class ANEW_ARRAY extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        OPStack stack = frame.getOpStack();
        int length = stack.popInt();
        if (length < 0) {
            throw new NegativeArraySizeException();
        }
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClazzRef classRef = (ClazzRef) constantPool.getConstant(index);
        Clazz newClazz = classRef.resolvedClass();
        Clazz arrayClazz = ClazzHelper.toArrayClazz(newClazz);
        Object array = arrayClazz.newArray(length);
        stack.pushRef(array);
    }
}
