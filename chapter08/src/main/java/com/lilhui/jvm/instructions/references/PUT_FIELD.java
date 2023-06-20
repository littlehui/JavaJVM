package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.Field;
import com.lilhui.jvm.rtda.heap.Object;
import com.lilhui.jvm.rtda.heap.Slots;
import com.lilhui.jvm.rtda.heap.constant.FieldRef;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/14 17:58
 */
// **************************************************
// 用于给对象的实例字段赋值
// 有三个操作数
// 1.U2的常量池索引(来自字节码)
// 2.要赋的值(操作数栈顶)
// 3.对象引用(操作数次栈顶)
// 根据常量池索引拿到字段引用并解析，判断解析出的字段是否合法
// 不合法则报错。合法则根据字段类型弹出要赋的值，再弹出对象引用.
// 若对象引用为空，报错。否则给其相应字段赋值。
public class PUT_FIELD extends U2IndexInstruction {

    @Override
    public void execute(Frame frame) {
        OPStack stack = frame.getOpStack();

        FieldRef fieldRef = (FieldRef) frame.getMethod().getClazz().getConstantPool().getConstant(getIndex());
        Field field = fieldRef.resolvedField();
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        if (field.isFinal()) {
            //TODO
            if (frame.getMethod().getClazz() != field.getClazz() || !"<init>".equals(frame.getMethod().getName())) {
                throw new IllegalAccessError();
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        Slots instanceSlots = null;
        Object ref = null;
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'S':
            case 'C':
            case 'I':
                int intValue = stack.popInt();
                ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException();
                }
                instanceSlots = ref.getFields();
                instanceSlots.setInt(slotId, intValue);
                break;
            case 'J':
                long longValue = stack.popInt();
                ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException();
                }
                instanceSlots = ref.getFields();
                instanceSlots.setLong(slotId, longValue);
                break;
            case 'F':
                float floatValue = stack.popFloat();
                ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException();
                }
                instanceSlots = ref.getFields();
                instanceSlots.setFloat(slotId, floatValue);
                break;
            case 'D':
                double doubleValue = stack.popDouble();
                ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException();
                }
                instanceSlots = ref.getFields();
                instanceSlots.setDouble(slotId, doubleValue);
                break;
            default:
                Object refValue = stack.popRef();
                ref = stack.popRef();
                if (ref == null) {
                    throw new NullPointerException();
                }
                instanceSlots = ref.getFields();
                instanceSlots.setRef(slotId, refValue);
                break;
        }
    }
}
