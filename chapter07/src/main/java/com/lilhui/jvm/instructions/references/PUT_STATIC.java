package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.Slot;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Field;
import com.lilhui.jvm.rtda.heap.Slots;
import com.lilhui.jvm.rtda.heap.constant.FieldRef;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/14 17:44
 */
// **************************************************
//指令用于修改某个类的静态变量值
//操作数为U2类型的运行时常量池索引(字节码中),要修改的值(操作数栈顶)
//根据索引找到字段引用，解析字段引用获取字段信息，判断该字段是否能被
//合法访问，不能则报错。根据该字段的descriptor判断字段类型，从操作数
//栈将该类型变量弹出，放入以字段的SlotId为索引的字段所在类的staticVars中
public class PUT_STATIC extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        FieldRef fieldRef = (FieldRef) frame.getMethod().getClazz().getConstantPool().getConstant(getIndex());
        Field field = fieldRef.resolvedField();
        Clazz clazz = field.getClazz();
        OPStack stack = frame.getOpStack();

        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        if (field.isFinal()) {
            if (frame.getMethod().getClazz() != clazz || !"<clinit>".equals(frame.getMethod().getName())) {
                throw new IllegalAccessError();
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slots staticVars = clazz.getStaticVars();
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'S':
            case 'C':
            case 'I':
                staticVars.setInt(slotId, stack.popInt());
                break;
            case 'J':
                staticVars.setLong(slotId, stack.popLong());
                break;
            case 'F':
                staticVars.setFloat(slotId, stack.popFloat());
                break;
            case 'D':
                staticVars.setDouble(slotId, stack.popDouble());
                break;
            case 'L':
            case '[':
                staticVars.setRef(slotId, stack.popRef());
                break;
        }
    }

}
