package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 14:38
 */
@Data
public class Method extends ClassMember{

    private int maxStack;
    private int maxLocals;
    private byte[] code;

    public Method(Clazz clazz, MemberInfo memberInfo) {
        super(clazz);
        this.copyMemberInfo(memberInfo);
        this.copyAttributes(memberInfo);
    }

    public static Method[] newMethods(Clazz clazz, MemberInfo[] memberInfos) {
        Method[] methods = new Method[memberInfos.length];
        for (int i=0; i<memberInfos.length; i++) {
            methods[i] = new Method(clazz, memberInfos[i]);
        }
        return methods;
    }

    private void copyAttributes(MemberInfo methodInfo) {
        Attributes.AttributeCode attributeCode = methodInfo.attributeCode();
        //本地方法会为null
        if (attributeCode != null) {
            this.maxStack = attributeCode.getMaxStack();
            this.maxLocals = attributeCode.getMaxLocals();
            this.code = attributeCode.getCode();
        }
    }

    private byte[] getCode(Attribute[] attributes) {
        for (Attribute attr : attributes) {
            if (attr instanceof Attributes.AttributeCode) {
                return ((Attributes.AttributeCode) attr).getCode();
            }
        }
        return null;
    }

    // Getters

    public byte[] getCode() {
        return code;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }
}
