package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.MemberInfo;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 14:35
 */
public class ClassMember {

    private int accessFlags;
    private String descriptor;
    private String name;
    private Clazz clazz;

    public void copyMemberInfo(MemberInfo memberInfo) {
        this.accessFlags = memberInfo.getAccessFlags();
        this.name = memberInfo.getName();
        this.descriptor = memberInfo.getDescriptor();
    }

    // Constructors
    public ClassMember(Clazz clazz) {
        this.clazz = clazz;
    }

    // Getters and Setters
    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Methods

    public boolean isAccessibleTo(Clazz clazz) {
        if (isPublic()) {
            return true;
        } else if (isProtected()) {
            return this.clazz.getPackageName().equals(clazz.getPackageName()) || clazz.isSubClassOf(this.clazz);
        } else if (isPrivate()) {
            return this.clazz == clazz;
        } else {
            return this.clazz.getPackageName().equals(clazz.getPackageName());
        }
    }

    // Getters

    public Clazz getClazz() {
        return clazz;
    }

    // Access Flags

    public boolean isPublic() {
        return (accessFlags & AccessFlags.ACC_PUBLIC) != 0;
    }

    public boolean isPrivate() {
        return (accessFlags & AccessFlags.ACC_PRIVATE) != 0;
    }

    public boolean isProtected() {
        return (accessFlags & AccessFlags.ACC_PROTECTED) != 0;
    }

    public boolean isStatic() {
        return (accessFlags & AccessFlags.ACC_STATIC) != 0;
    }

    public boolean isFinal() {
        return (accessFlags & AccessFlags.ACC_FINAL) != 0;
    }

    public boolean isSynthetic() {
        return (accessFlags & AccessFlags.ACC_SYNTHETIC) != 0;
    }
}
