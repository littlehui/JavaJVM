package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.*;
import com.lilhui.jvm.classfile.ClassLoader;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 13:49
 */
@Data
public class Clazz {
    private int accessFlags;
    private String name;
    private String superClassName;
    private String[] interfaceNames;

    private ConstantPool constantPool;
    private Field[] fields;
    private Method[] methods;
    private ClassLoader loader;
    private Clazz superClazz;
    private Clazz[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private Slots staticVars;
    private boolean initializationFlag;
    // Constructors

    public Clazz(ClassFile cf) {
        this.accessFlags = cf.getAccessFlags();
        this.name = cf.getClassName();
        this.superClassName = cf.getSuperClassName();
        this.interfaceNames = cf.getInterfaceNames();
        this.constantPool = new ConstantPool(this, cf.getConstantPoolInfo());
        this.fields = Field.newFields(this, cf.getFields());
        this.methods = Method.newMethods(this, cf.getMethods());
        this.initializationFlag = false;
    }

    // Public Methods
    public Object newObject() {
        Object obj = new Object();
        obj.setClazz(this);
        obj.setFileds(new Slots(instanceSlotCount));
        return obj;
    }

    // Access Check
    public boolean isAccessibleTo(Clazz clazz) {
        if (isPublic()) {
            return true;
        }
        if (getPackageName().equals(clazz.getPackageName())) {
            return true;
        }
        return false;
    }

    public boolean isSubClassOf(Clazz clazz) {
        for (Clazz c = superClazz; c != null; c = c.superClazz) {
            if (c == clazz) {
                return true;
            }
        }
        return false;
    }

    public boolean isImplements(Clazz iface) {
        for (Clazz c = this; c != null; c = c.superClazz) {
            for (Clazz i : c.interfaces) {
                if (iface == i || i.isSubInterfaceOf(iface)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSubInterfaceOf(Clazz iface) {
        for (Clazz i : interfaces) {
            if (i == iface || i.isSubInterfaceOf(iface)) {
                return true;
            }
        }
        return false;
    }

    // Package Name
    public String getPackageName() {
        int index = name.lastIndexOf("/");
        if (index == -1) {
            return "";
        }
        return name.substring(0, index);
    }

    // Getters

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public Slots getStaticVars() {
        return staticVars;
    }

    public Method[] getMethods() {
        return methods;
    }

    // Access Flags
    public boolean isPublic() {
        return (accessFlags & AccessFlags.ACC_PUBLIC) != 0;
    }

    public boolean isFinal() {
        return (accessFlags & AccessFlags.ACC_FINAL) != 0;
    }

    public boolean isSuper() {
        return (accessFlags & AccessFlags.ACC_SUPER) != 0;
    }

    public boolean isInterface() {
        return (accessFlags & AccessFlags.ACC_INTERFACE) != 0;
    }

    public boolean isAbstract() {
        return (accessFlags & AccessFlags.ACC_ABSTRACT) != 0;
    }

    public boolean isSynthetic() {
        return (accessFlags & AccessFlags.ACC_SYNTHETIC) != 0;
    }

    public boolean isAnnotation() {
        return (accessFlags & AccessFlags.ACC_ANNOTATION) != 0;
    }

    public boolean isEnum() {
        return (accessFlags & AccessFlags.ACC_ENUM) != 0;
    }

    public boolean isSuperClassOf(Clazz currentClazz) {
        return currentClazz.isSubClassOf(this);
    }
}
