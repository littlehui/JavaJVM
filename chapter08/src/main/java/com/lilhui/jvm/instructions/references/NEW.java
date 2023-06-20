package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.ClazzInitLogic;
import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Object;
import com.lilhui.jvm.rtda.heap.constant.ClazzRef;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/14 17:27
 */
// 指令码后两字节为运行时常量池索引,根据索引取出类符号引用。
// 解析类符号引用得到类数据，根据类数据创建类对象，将对象压入栈顶
public class NEW extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        ConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClazzRef classRef = (ClazzRef) cp.getConstant(getIndex());
        Clazz newClass = classRef.resolvedClass();
        if (!newClass.isInitializationFlag()) {
            frame.revertNextPc();
            ClazzInitLogic clazzInitLogic = new ClazzInitLogic();
            clazzInitLogic.initClass(frame.getThread(), newClass);
            return;
        }
        if (newClass.isAbstract() || newClass.isInterface()) {
            throw new InstantiationError();
        }
        Object obj = newClass.newObject();
        frame.getOpStack().pushRef(obj);
    }
}
