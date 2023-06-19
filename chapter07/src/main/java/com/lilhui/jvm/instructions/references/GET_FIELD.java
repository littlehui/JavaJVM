package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.Slot;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Field;
import com.lilhui.jvm.rtda.heap.Slots;
import com.lilhui.jvm.rtda.heap.constant.FieldRef;
import com.lilhui.jvm.rtda.heap.Object;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/14 17:55
 */
// **************************************************
// 获取对象字段的值，压入栈顶。
// 需要两个操作数。
// 1.U2的运行时常量池索引(字节码)。
// 2.对象引用(操作数栈顶)。
// 根据索引获取字段符号引用，解析得到字段，判断字段是否合法。
// 不合法报错。弹出对象引用，根据字段类型和SlotId从对象字段中
// 拿出值压入栈中。
public class GET_FIELD extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        OPStack stack = frame.getOpStack();
        Object ref = stack.popRef();
        if (ref == null) {
            throw new NullPointerException();
        }

        FieldRef fieldRef = (FieldRef) frame.getMethod().getClazz().getConstantPool().getConstant(getIndex());
        Field field = fieldRef.resolvedField();
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }

        int slotId = field.getSlotId();
        String descriptor = field.getDescriptor();
        Slots instanceSlots = ref.getFileds();
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'S':
            case 'C':
            case 'I':
                stack.pushInt(instanceSlots.getInt(slotId));
                break;
            case 'J':
                stack.pushLong(instanceSlots.getLong(slotId));
                break;
            case 'F':
                stack.pushFloat(instanceSlots.getFloat(slotId));
                break;
            case 'D':
                stack.pushDouble(instanceSlots.getDouble(slotId));
                break;
            default:
                stack.pushRef(instanceSlots.getRef(slotId));
                break;
        }
    }
}
