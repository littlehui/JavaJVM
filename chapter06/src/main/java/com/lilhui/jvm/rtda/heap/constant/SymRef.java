package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.Constant;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 17:13
 */
@Getter
@Setter
public class SymRef implements Constant {

    private ConstantPool constantPool;

    private String className;

    private Clazz resolvedClass;

    public Clazz resolvedClass() {
        if (resolvedClass == null) {
            resolveClassRef();
        }
        return resolvedClass;
    }

    private void resolveClassRef() {
        Clazz currentClass = constantPool.getClazz();
        Clazz refClass = currentClass.getLoader().loadClass(className);
        if (refClass.isAccessibleTo(currentClass)) {
            resolvedClass = refClass;
        } else {
            throw new IllegalAccessError();
        }
    }

    @Override
    public <T> void setValue(T value) {

    }

    @Override
    public SymRef getValue() {
        return this;
    }
}
