package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.MethodInvokeLogic;
import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Method;
import com.lilhui.jvm.rtda.heap.constant.MethodRef;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/16 17:34
 */
public class INVOKE_STATIC extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(getIndex());
        Method resolvedMethod = methodRef.resolvedMethod();
        if (!resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        MethodInvokeLogic methodInvokeLogic = new MethodInvokeLogic();
        methodInvokeLogic.invokeMethod(frame, resolvedMethod);
    }
}
