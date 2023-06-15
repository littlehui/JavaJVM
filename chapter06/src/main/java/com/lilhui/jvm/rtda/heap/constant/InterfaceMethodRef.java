package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.classfile.constant.ConstantInterfaceMethodrefInfo;
import com.lilhui.jvm.rtda.heap.Constant;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Method;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 17:44
 */
public class InterfaceMethodRef extends MemberRef {

    private Method method;

    public InterfaceMethodRef(ConstantPool cp, ConstantInterfaceMethodrefInfo interfaceMethodInfo) {
        super();
        setConstantPool(cp);
        setClassName(interfaceMethodInfo.getClassName());
        setName(interfaceMethodInfo.getNameAndType());
        setDescriptor("");
    }

}
