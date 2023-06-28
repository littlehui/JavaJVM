package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.MethodInvokeLogic;
import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Method;
import com.lilhui.jvm.rtda.heap.Object;
import com.lilhui.jvm.rtda.heap.StringPool;
import com.lilhui.jvm.rtda.heap.constant.MethodLookup;
import com.lilhui.jvm.rtda.heap.constant.MethodRef;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/15 10:26
 */
public class INVOKE_VIRTUAL extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) cp.getConstant(getIndex());
        Method method = methodRef.resolvedMethod();
        if (method.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        OPStack stack = frame.getOpStack();
        int argSlotCount = method.getArgSlotCount();
        Object ref = stack.getRefFromTop(argSlotCount - 1);
        if (ref == null) {
            if (method.getName().equals("println")) {
                println(stack, method.getDescriptor());
                return;
            }
            throw new NullPointerException();
        }
        // Class<?> currentClass = frame.getMethod().getClass();
        // Class<?> newClass = methodRef.getClazz();
        // if (method.isProtected() &&
        //         currentClass.isSubclassOf(method.getClazz()) &&
        //         !method.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
        //         ref.getClass() != currentClass &&
        //         !currentClass.isSubclassOf(ref.getClass())) {
        //     throw new IllegalAccessError();
        // }
        Method invokeMethod = MethodLookup.lookupMethodInClass(ref.getClazz(), method.getName(), method.getDescriptor());
        if (invokeMethod == null || invokeMethod.isAbstract()) {
            throw new AbstractMethodError();
        }
        MethodInvokeLogic methodInvokeLogic = new MethodInvokeLogic();
        methodInvokeLogic.invokeMethod(frame, invokeMethod);
    }

    private void println(OPStack stack, String descriptor) {
        switch (descriptor) {
            case "(Z)V":
                System.out.printf("%b%n", stack.popInt() != 0);
                break;
            case "(C)V":
                System.out.printf("%c%n", (char) stack.popInt());
                break;
            case "(B)V":
                System.out.printf("%d%n", (byte) stack.popInt());
                break;
            case "(S)V":
                System.out.printf("%d%n", (short) stack.popInt());
                break;
            case "(I)V":
                System.out.printf("%d%n", stack.popInt());
                break;
            case "(J)V":
                System.out.printf("%d%n", stack.popLong());
                break;
            case "(F)V":
                System.out.printf("%f%n", stack.popFloat());
                break;
            case "(D)V":
                System.out.printf(stack.popDouble() + "");
                break;
            case "(Ljava/lang/String;)V":
                Object jvmStr = stack.popRef();
                String str = StringPool.jvmStrToString(jvmStr);
                System.out.printf("%s%n", str);
                break;
            default:
                throw new RuntimeException("println: " + descriptor);
        }
        stack.popRef();
    }
}
