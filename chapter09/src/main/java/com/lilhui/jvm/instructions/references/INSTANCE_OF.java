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
 * @date 2023/6/15 10:12
 */
// **************************************************
// 判断对象是否是类的实例
// 2个操作数 运行时常量池索引(字节码) 对象引用(操作数栈顶)
// 根据索引拿到类符号引用，解析后得到类数据。
// 判断对象是否是类的实例，将结果压栈。(0:不是 1:是)
public class INSTANCE_OF extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        ClazzRef clazzRef = (ClazzRef) frame.getMethod().getClazz().getConstantPool().getConstant(getIndex());
        Clazz clazz = clazzRef.resolvedClass();
        OPStack opStack = frame.getOpStack();
        com.lilhui.jvm.rtda.heap.Object object = opStack.popRef();
        if (object == null) {
            opStack.pushInt(0);
            return;
        }
        if (object.isInstanceOf(clazz)) {
            opStack.pushInt(1);
        } else {
            opStack.pushInt(0);
        }
    }
}
