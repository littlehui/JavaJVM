package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.MethodInvokeLogic;
import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Method;
import com.lilhui.jvm.rtda.heap.constant.MethodLookup;
import com.lilhui.jvm.rtda.heap.constant.MethodRef;
import com.lilhui.jvm.rtda.heap.Object;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/15 10:21
 */
public class INVOKE_SPECIAL extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        Clazz currentClazz = frame.getMethod().getClazz();
        ConstantPool constantPool = currentClazz.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(getIndex());
        Clazz resolvedClazz = methodRef.resolvedClass();
        Method resolvedMethod = methodRef.resolvedMethod();
        if (resolvedMethod.getName().equals("<init>") && resolvedMethod.getClazz() != resolvedClazz) {
            throw new NoSuchMethodError();
        }
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        Object ref = frame.getOpStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);

        if (ref == null) {
            throw new NullPointerException();
        }

        if (resolvedMethod.isProtected()
        && resolvedMethod.getClazz().isSubClassOf(currentClazz)
        && !resolvedMethod.getClazz().getPackageName().equals(currentClazz.getPackageName())
        && !ref.getClazz().isSubClassOf(currentClazz)) {
            throw new IllegalAccessError();
        }

/*        调用的中超类中的函数，但不是构造函数，且当前类的
        ACC_SUPER标志被设置，需要一个额外的过程查找最终要调用的
        方法；否则前面从方法符号引用中解析出来的方法就是要调用的方
        法。*/
        Method methodToBeInvoked = resolvedMethod;
        if (currentClazz.isSuper()
        && resolvedClazz.isSuperClassOf(currentClazz)
        && !resolvedMethod.getName().equals("<init>")) {
            methodToBeInvoked = MethodLookup.lookupMethodInClass(currentClazz.getSuperClazz()
            , methodRef.getName()
            , methodRef.getDescriptor());
        }

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }
        MethodInvokeLogic methodInvokeLogic = new MethodInvokeLogic();
        methodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
