package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.constant.ClazzRef;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/15 10:19
 */
// **************************************************
// 检查对象是否可以转换成某一类型，不能则报错
// 有两个操作数 运行时常量池索引(字节码) 对象引用(栈顶)
// 用索引拿到类符号引用，解析，出栈拿到对象引用再入栈。
// 如果对象引用为空，可以转换。
// 如果对象是类的实例，可以转换
// 否则不能转换，报错
public class CHECK_CAST extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        ClazzRef clazzRef = (ClazzRef) frame.getMethod().getClazz().getConstantPool().getConstant(getIndex());
        Clazz clazz = clazzRef.resolvedClass();
        OPStack opStack = frame.getOpStack();
        com.lilhui.jvm.rtda.heap.Object object = opStack.popRef();
        opStack.pushRef(object);
        if (object == null) {
            return;
        }
        if (!object.isInstanceOf(clazz)) {
            throw new ClassCastException();
        }
    }
}
