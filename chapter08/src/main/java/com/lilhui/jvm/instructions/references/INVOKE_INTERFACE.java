package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.Instruction;
import com.lilhui.jvm.instructions.base.MethodInvokeLogic;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Method;
import com.lilhui.jvm.rtda.heap.Object;
import com.lilhui.jvm.rtda.heap.constant.InterfaceMethodRef;
import com.lilhui.jvm.rtda.heap.constant.MethodLookup;
import lombok.Getter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/16 18:01
 */
public class INVOKE_INTERFACE implements Instruction {

    @Getter
    private int index;

    @Override
    public void readOperands(CodeReader reader) {
        this.index = reader.readU2();
        reader.readU1(); // count
        reader.readU1(); // must be 0
    }

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) cp.getConstant(this.getIndex());
        Method method = methodRef.resolvedInterfaceMethod();
        if (method.isStatic() || method.isPrivate()) {
            throw new IncompatibleClassChangeError();
        }
        OPStack stack = frame.getOpStack();
        int argSlotCount = method.getArgSlotCount();
        Object ref = stack.getRefFromTop(argSlotCount - 1);
        if (ref == null) {
            throw new NullPointerException();
        }
        if (!ref.getClazz().isImplements(method.getClazz())) {
            throw new IncompatibleClassChangeError();
        }
        Method invokeMethod = MethodLookup.lookupMethodInClass(ref.getClazz(), method.getName(), method.getDescriptor());
        if (invokeMethod == null || invokeMethod.isAbstract()) {
            throw new AbstractMethodError();
        }
        if (!invokeMethod.isPublic()) {
            throw new IllegalAccessError();
        }
        MethodInvokeLogic methodInvokeLogic = new MethodInvokeLogic();
        methodInvokeLogic.invokeMethod(frame, invokeMethod);
    }
}
