package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.classfile.constant.ConstantFieldrefInfo;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.ConstantPool;
import com.lilhui.jvm.rtda.heap.Field;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 17:19
 */
public class FieldRef extends MemberRef {

    private Field field;

    public FieldRef(ConstantPool cp, ConstantFieldrefInfo fieldInfo) {
        super();
        setConstantPool(cp);
        setClassName(fieldInfo.getClassName());
        int nameAndTypeLength = fieldInfo.getNameAndType().length();
        String nameAndType = fieldInfo.getNameAndType();
        //"outLjava/io/PrintStream;"
        //"startValueI";
        int typeIndex = takeTypeIndex(nameAndType);
        String name = nameAndType.substring(0, typeIndex);
        String descriptor = nameAndType.substring(typeIndex);
        setName(name);
        setDescriptor(descriptor);
    }

    private int takeTypeIndex(String nameAndType) {
        String nameAndTypeTemp = nameAndType;
        if (nameAndType.indexOf("/") > 0) {
            nameAndTypeTemp= nameAndType.substring(0, nameAndType.indexOf("/"));
        }
        String[] types = new String[]{"Z","B","S","C","I","J","F","D","L"};
        for (String typeShot : types) {
            if (nameAndTypeTemp.indexOf(typeShot) > 0) {
                return nameAndTypeTemp.indexOf(typeShot);
            }
        }
        return 0;
    }

    public Field resolvedField() {
        if (field == null) {
            resolveFieldRef();
        }
        return field;
    }

    private void resolveFieldRef() {
        Clazz currentClass = getConstantPool().getClazz();
        resolvedClass();
        Field resolvedField = findField(getResolvedClass(), getName(), getDescriptor());
        if (resolvedField == null) {
            throw new NoSuchFieldError();
        }
        if (!resolvedField.isAccessibleTo(currentClass)) {
            throw new IllegalAccessError();
        }
        field = resolvedField;
    }

    private Field findField(Clazz clazz, String name, String descriptor) {
        for (Field field : clazz.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }
        for (Clazz interfaceClazz : clazz.getInterfaces()) {
            Field field = findField(interfaceClazz, name, descriptor);
            if (field != null) {
                return field;
            }
        }
        if (clazz.getSuperClazz() != null) {
            return findField(clazz.getSuperClazz(), name, descriptor);
        }
        return null;
    }
}
