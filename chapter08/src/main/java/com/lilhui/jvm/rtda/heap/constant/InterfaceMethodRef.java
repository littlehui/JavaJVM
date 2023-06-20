package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.classfile.constant.ConstantInterfaceMethodrefInfo;
import com.lilhui.jvm.rtda.heap.Clazz;
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
        String nameAndType = interfaceMethodInfo.getNameAndType();
        String name = nameAndType.substring(0, nameAndType.indexOf("("));
        String descriptor = nameAndType.substring(nameAndType.indexOf("("));
        setName(name);
        setDescriptor(descriptor);
    }

    public Method resolvedInterfaceMethod() {
        if (method == null) {
            this.resolveInterfaceMethodRef();
        }
        return method;
    }

    private void resolveInterfaceMethodRef() {
        Clazz clazz = this.getConstantPool().getClazz();
        Clazz resolvedClazz = this.getResolvedClass();
        if (!resolvedClazz.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        method = lookupInterfaceMethod(resolvedClazz, this.getName(), this.getDescriptor());
        if (method == null) {
            throw new NoSuchMethodError();
        }
        if (!method.isAccessibleTo(clazz)) {
            throw new IllegalAccessError();
        }
        this.method = method;
    }

    private Method lookupInterfaceMethod(Clazz interfaceClazz, String name, String descriptor) {
        for (Method method : interfaceClazz.getMethods()) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return MethodLookup.lookupMethodInInterfaces(interfaceClazz.getInterfaces(), name, descriptor);
    }
}
