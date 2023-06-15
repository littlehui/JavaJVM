package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.ConstantPool;
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
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(getIndex());
        if (methodRef.getName().equals("println")) {
            OPStack stack = frame.getOpStack();
            String descriptor = methodRef.getDescriptor();
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
                    System.out.printf("%f%n", stack.popDouble());
                    break;
                case "(Ljava/lang/String;)V":
                    System.out.printf("%s%n", stack.popRef());
                    break;
                default:
                    throw new RuntimeException("println: " + descriptor);
            }
            //stack.popRef();
        }
    }
}
