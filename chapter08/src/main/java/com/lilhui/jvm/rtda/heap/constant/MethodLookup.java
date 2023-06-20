package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.Method;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/16 16:37
 */
public class MethodLookup {

    public static Method lookupMethodInClass(Clazz clazz, String name, String descriptor) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        Clazz superClazz = clazz.getSuperClazz();
        if (superClazz != null) {
            for (Method method : superClazz.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }

    public static Method lookupMethodInInterfaces(Clazz[] interfaceClazzs, String name, String descriptor) {
        for (Clazz interfaceClazz : interfaceClazzs) {
            for (Method method : interfaceClazz.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
            Method method = lookupMethodInInterfaces(interfaceClazz.getInterfaces(), name, descriptor);
            if (method != null) {
                return method;
            }
        }
        return null;
    }
}
