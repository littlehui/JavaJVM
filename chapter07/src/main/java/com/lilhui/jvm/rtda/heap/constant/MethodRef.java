package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.classfile.constant.ConstantMethodrefInfo;
import com.lilhui.jvm.rtda.heap.Clazz;
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

    public Method resolvedMethod() {
        if (this.method == null)  {
            this.resolvedMethodRef();
        }
        return method;
    }

    private void resolvedMethodRef() {
        Clazz clazz = this.getConstantPool().getClazz();
        Clazz resolvedClazz = this.resolvedClass();
        if (resolvedClazz.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        Method method = lookupMethod(resolvedClazz, this.getName(), this.getDescriptor());
        if (method == null) {
            throw new NoSuchMethodError();
        }
        if (!method.isAccessibleTo(clazz)) {
            throw new IllegalAccessError();
        }
        this.method = method;
    }

    private Method lookupMethod(Clazz clazz, String name, String descriptor) {
        //先找类里是否有method
        Method method = MethodLookup.lookupMethodInClass(clazz, name, descriptor);
        if (method == null) {
            //再找是否在继承接口里
            method = MethodLookup.lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
        }
        return method;
    }


}
