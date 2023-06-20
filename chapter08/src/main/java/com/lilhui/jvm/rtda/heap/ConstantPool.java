package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.ConstantPoolInfo;
import com.lilhui.jvm.classfile.constant.*;
import com.lilhui.jvm.rtda.heap.constant.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 15:38
 */
@Getter
@Setter
public class ConstantPool {
    private Constant[] constants;
    private Clazz clazz;

    public ConstantPool(Clazz clazz, ConstantPoolInfo clazzFileConstantPoolInfo) {
        int length = clazzFileConstantPoolInfo.getSize();
        this.constants = new Constant[length];
        this.clazz = clazz;

        for (int i = 1; i < length; i++) {
            ConstantInfo cpInfo = clazzFileConstantPoolInfo.getConstantInfo(i);
            if (cpInfo instanceof ConstantIntegerInfo) {
                constants[i] = new ConstantInteger(((ConstantIntegerInfo) cpInfo).getValue());
            } else if (cpInfo instanceof ConstantLongInfo) {
                constants[i] = new ConstantLong(((ConstantLongInfo) cpInfo).getValue());
                i++;
            } else if (cpInfo instanceof ConstantFloatInfo) {
                constants[i] = new ConstantFloat(((ConstantFloatInfo) cpInfo).getValue());
            } else if (cpInfo instanceof ConstantDoubleInfo) {
                constants[i] = new ConstantDouble(((ConstantDoubleInfo) cpInfo).getValue());
                i++;
            } else if (cpInfo instanceof ConstantStringInfo) {
                constants[i] = new ConstantString(((ConstantStringInfo) cpInfo).getString());
            } else if (cpInfo instanceof ConstantClassInfo) {
                constants[i] = Constants.newClassRef(this, cpInfo);
            } else if (cpInfo instanceof ConstantFieldrefInfo) {
                constants[i] = Constants.newFieldRef(this, cpInfo);
            } else if (cpInfo instanceof ConstantMethodrefInfo) {
                constants[i] = Constants.newMethodRef(this, cpInfo);
            } else if (cpInfo instanceof ConstantInterfaceMethodrefInfo) {
                constants[i] = Constants.newInterfaceMethodRef(this, cpInfo);
            }
        }
    }

    public Constant getConstant(int index) {
        if (index > 0 && index < constants.length) {
            return constants[index];
        }
        return null;
    }
}
