package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.classfile.constant.*;
import com.lilhui.jvm.rtda.heap.Constant;
import com.lilhui.jvm.rtda.heap.ConstantPool;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 16:41
 */
public class Constants {
    
    public static Constant newClassRef(ConstantPool constantPool, ConstantInfo cpInfo) {
        ConstantClassInfo classInfo = (ConstantClassInfo) cpInfo;
        ClazzRef clazzRef = new ClazzRef(constantPool, classInfo);
        return clazzRef;
    }

    public static Constant newFieldRef(ConstantPool constantPool, ConstantInfo cpInfo) {
        ConstantFieldrefInfo fieldRefInfo = (ConstantFieldrefInfo) cpInfo;
        FieldRef fieldRef = new FieldRef(constantPool, fieldRefInfo);
        return fieldRef;
    }

    public static Constant newMethodRef(ConstantPool constantPool, ConstantInfo cpInfo) {
        ConstantMethodrefInfo methodRefInfo = (ConstantMethodrefInfo) cpInfo;
        MethodRef methodRef = new MethodRef(constantPool, methodRefInfo);
        return methodRef;
    }

    public static Constant newInterfaceMethodRef(ConstantPool constantPool, ConstantInfo cpInfo) {
        ConstantInterfaceMethodrefInfo interfaceMethodRefInfo = (ConstantInterfaceMethodrefInfo) cpInfo;
        InterfaceMethodRef interfaceMethodRef = new InterfaceMethodRef(constantPool, interfaceMethodRefInfo);
        return interfaceMethodRef;
    }


    public static Constant newConstantInvokeDynamic(ConstantPool constantPool, ConstantInfo cpInfo) {

        return null;
    }
}
