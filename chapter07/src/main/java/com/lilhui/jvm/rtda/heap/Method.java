package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.*;
import lombok.*;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 14:38
 */
@Getter
@Setter
public class Method extends ClassMember{

    private int maxStack;
    private int maxLocals;
    private byte[] code;
    private int argSlotCount;

    public Method(Clazz clazz, MemberInfo memberInfo) {
        super(clazz);
        this.copyMemberInfo(memberInfo);
        this.copyAttributes(memberInfo);
        this.calcArgSlotCount();
    }

    private void calcArgSlotCount() {
        String descriptor = this.getDescriptor();
        String args = descriptor.substring(1, descriptor.lastIndexOf(")"));
        int count = 0;

        for (int i = 0; i < args.length(); i++) {
            char arg = args.charAt(i);
            switch (arg) {
                case 'Z':
                case 'B':
                case 'S':
                case 'C':
                case 'I':
                case 'F':
                    count++;
                    break;
                case 'J':
                case 'D':
                    count += 2;
                    break;
                case 'L':
                    count++;
                    i += args.substring(i).indexOf(";");  // Find the index of the next semicolon
                    break;
                case '[':
                    count++;
                    int j = i + 1;
                    while (args.charAt(j) == '[') {
                        j++;
                    }
                    i = j;
                    if (args.charAt(j) == 'L') {
                        i += args.substring(i).indexOf(";");  // Find the index of the next semicolon
                    }
                    break;
            }
        }
        if (!isStatic()) {
            count++;
        }
        this.argSlotCount = count;
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
