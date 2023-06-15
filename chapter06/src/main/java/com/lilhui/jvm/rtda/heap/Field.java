package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.Attributes;
import com.lilhui.jvm.classfile.FieldInfo;
import com.lilhui.jvm.classfile.MemberInfo;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 14:55
 */
@Data
public class Field extends ClassMember {

    private int slotId;

    private int constantValueIndex;

    public Field(Clazz clazz, MemberInfo fieldInfo) {
        super(clazz);
        this.copyMemberInfo(fieldInfo);
        this.copyAttributes(fieldInfo);
    }

    public static Field[] newFields(Clazz clazz, FieldInfo[] memberInfos) {
        Field[] fields = new Field[memberInfos.length];
        for (int i = 0; i < memberInfos.length; i++) {
            fields[i] = new Field(clazz, memberInfos[i]);
        }
        return fields;
    }

    private void copyAttributes(MemberInfo methodInfo) {
        Attributes.AttributeConstantValue attributeConstantValue = methodInfo.attributeConstantValue();
        if (attributeConstantValue != null) {
            this.constantValueIndex = attributeConstantValue.getConstantvalueIndex();
        }
    }

    public boolean isLongOrDouble() {
        switch (getDescriptor()) {
            case "J":
            case "D":
                return true;
            default:
                return false;
        }
    }
}
