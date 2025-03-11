package com.lilhui.jvm.instructions.reserved;

import com.lilhui.jvm.instructions.base.MethodInvokeLogic;
import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.nativej.NativeMethod;
import com.lilhui.jvm.nativej.Registry;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.heap.Method;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/28 14:45
 */
public class INVOKE_NATIVE extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        Method method = frame.getMethod();
        String clazzName = method.getClazz().getName();
        String methodName = method.getName();
        String methodDescriptor = method.getDescriptor();
        NativeMethod nativeMethod = Registry.findNativeMethod(clazzName, methodName, methodDescriptor);
        if (nativeMethod == null) {
            String methodInfo = clazzName + "." + methodName + methodDescriptor;
            throw new UnsatisfiedLinkError(methodInfo);
        }
        try {
            nativeMethod.invoke(frame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
