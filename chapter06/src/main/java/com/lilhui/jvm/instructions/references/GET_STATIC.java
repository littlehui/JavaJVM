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
 * @date 2023/6/14 17:35
 */
// **************************************************
// 指令用于获得某个类的静态变量值
// 操作数为U2的运行时常量池索引
// 根据索引获取字段引用，解析字段引用获取字段信息，判断该字段是否能被
// 合法访问，不能则报错。根据该字段的descriptor判断字段类型，
// 从字段所在类的staticVars中获得索引为Field.SlotId的值，将值压栈
public class GET_STATIC extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        FieldRef fieldRef = (FieldRef) frame.getMethod().getClazz().getConstantPool().getConstant(getIndex());
        Field field = fieldRef.resolvedField();
        Clazz clazz = field.getClazz();
        OPStack stack = frame.getOpStack();

        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slots slots = clazz.getStaticVars();
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'S':
            case 'C':
            case 'I':
                stack.pushInt(slots.getInt(slotId));
                break;
            case 'J':
                stack.pushLong(slots.getLong(slotId));
                break;
            case 'F':
                stack.pushFloat(slots.getFloat(slotId));
                break;
            case 'D':
                stack.pushDouble(slots.getDouble(slotId));
                break;
            case 'L':
            case '[':
                stack.pushRef(slots.getRef(slotId));
                break;
        }
    }
}
