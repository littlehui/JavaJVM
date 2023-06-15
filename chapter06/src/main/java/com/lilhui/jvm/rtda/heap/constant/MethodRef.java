package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.classfile.constant.ConstantMethodrefInfo;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Method;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 17:36
 */
public class MethodRef extends MemberRef {

    private Method method;

    public MethodRef(ConstantPool cp, ConstantMethodrefInfo methodInfo) {
        super();
        setConstantPool(cp);
        setClassName(methodInfo.getClassName());
        String nameAndType = methodInfo.getNameAndType();
        String name = nameAndType.substring(0, nameAndType.indexOf("("));
        String descriptor = nameAndType.substring(nameAndType.indexOf("("));
        setName(name);
        setDescriptor(descriptor);
    }

}
