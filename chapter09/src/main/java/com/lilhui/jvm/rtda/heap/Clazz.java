package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.ClassFile;
import com.lilhui.jvm.classfile.ClazzLoader;
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
    private ClazzLoader loader;
    private Clazz superClazz;
    private Clazz[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private Slots staticVars;
    private boolean initializationFlag;
    //class类关于该类的实例
    private Object jClazz;
    // Constructors

    public Clazz() {

    }
    public boolean isArray() {
        return false;
    }

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
        Object obj = new Object(this);
        return obj;
    }

    public Object newArray(int count) {
        throw new UnsupportedOperationException();
    }

    public Clazz getComponentClazz() {
        String componentClazzName = ClazzHelper.getComponentClazzName(this.getName());
        return this.getLoader().loadClass(componentClazzName);
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

    public void startInit() {
        this.initializationFlag = true;
    }

    public Method getClinitMethod() {
        for (Method method : methods) {
            if (method.isSynthetic()) {
                if (method.getName().equals("<clinit>")
                && method.getDescriptor().equals("()V")) {
                    return method;
                }
            }
        }
        return null;
    }

    public boolean isAssignableFrom(Clazz otherClazz) {
        Clazz s = otherClazz;
        Clazz t = otherClazz;
        if (s == t) {
            return true;
        }
        if (!s.isArray()) {
            if (!s.isInterface()) {
                if (!t.isInterface()) {
                    return s.isSubClassOf(t);
                } else {
                    return s.isImplements(t);
                }
            } else {
                if (!t.isInterface()) {
                    return t.isJlObject();
                } else {
                    return s.isSubInterfaceOf(t);
                }
            }
        } else {
            if (!t.isArray()) {
                if (!t.isInterface()) {
                    return t.isJlObject();
                } else {
                    return t.isJlCloneable() || t.isJioSerializable();
                }
            } else {
                Clazz selfComponentClazz = s.getComponentClazz();
                Clazz otherComponentClazz = t.getComponentClazz();
                return selfComponentClazz == otherComponentClazz || otherComponentClazz.isAssignableFrom(s);
            }
        }
    }

    public boolean isJlObject() {
        return this.getName().equals("java/lang/Object");
    }

    public boolean isJlCloneable() {
        return this.getName().equals("java/lang/Cloneable");
    }

    public boolean isJioSerializable() {
        return this.getName().equals("java/io/Serializable");
    }

    public Field getField(String name, String descriptor, boolean isStatic) {
        for (Clazz c = this; c != null; c = c.getSuperClazz()) {
            for (Field field : c.fields) {
                if (field.isStatic() == isStatic && field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                    return field;
                }
            }
        }
        return null;
    }

    public ArrayClazz getArrayClazz() {
        String descriptor = "";;
        if (this.getName().charAt(0) == '[') {
            descriptor = this.getName();
        } else {
            descriptor = "L" + this.getName() + ";";
        }
        String arrayClazzName = "[" + descriptor;
        return (ArrayClazz) this.getLoader().loadClass(arrayClazzName);
    }
}
