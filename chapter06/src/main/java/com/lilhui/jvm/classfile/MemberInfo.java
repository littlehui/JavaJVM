package com.lilhui.jvm.classfile;

import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/26 13:51
 */
@Data
public class MemberInfo {
    ConstantPoolInfo constantPoolInfo;
    int accessFlags;
    int nameIndex;
    int descriptorIndex;
    Attribute[] attributes;

    public MemberInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    public void readInfo(BytecodeReader reader) {
        accessFlags = reader.readU2();
        nameIndex = reader.readU2();
        descriptorIndex = reader.readU2();
        int attributesCount = reader.readU2();
        attributes = new Attribute[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            int attributeNameIndex = reader.readU2();
            String attributeName = constantPoolInfo.getUtf8(attributeNameIndex);
            attributes[i] = newAttribute(attributeName, constantPoolInfo);
            attributes[i].readInfo(reader);
        }
    }

    public void printInfo() {
        System.out.println("access_flags: " + accessFlags);
        System.out.println("name_index: #" + nameIndex + "\t\t" + constantPoolInfo.getUtf8(nameIndex));
        System.out.println("descriptor_index: #" + descriptorIndex + "\t\t" + constantPoolInfo.getUtf8(descriptorIndex));
        System.out.println("attributes: #" + attributes.length);
        for (int index = 0; index < attributes.length; index++) {
            System.out.print("#" + index + "{");
            attributes[index].printInfo();
            System.out.println("}");
        }
    }

    public Attribute newAttribute(String attributeName, ConstantPoolInfo constantPoolInfo) {
        switch (attributeName) {
            case "Code":
                return new Attributes.AttributeCode(constantPoolInfo);
            case "Deprecated":
                return new Attributes.AttributeDeprecated();
            case "ConstantValue":
                return new Attributes.AttributeConstantValue();
            case "Exceptions":
                return new Attributes.AttributeExceptions();
            case "LineNumberTable":
                return new Attributes.AttributeLineNumberTable();
            case "LocalVariableTable":
                return new Attributes.AttributeLocalVariableTable();
            case "SourceFile":
                return new Attributes.AttributeSourceFile();
            default:
                return new Attributes.AttributeUnparsed();
        }
    }

    public String getName() {
        return constantPoolInfo.getUtf8(nameIndex);
    }

    public String getDescriptor() {
        return constantPoolInfo.getUtf8(descriptorIndex);
    }

    public Attributes.AttributeCode attributeCode() {
        for (Attribute attribute : attributes) {
            if (attribute instanceof Attributes.AttributeCode) {
                return (Attributes.AttributeCode) attribute;
            }
        }
        return null;
    }

    public Attributes.AttributeConstantValue attributeConstantValue() {
        for (Attribute attribute : attributes) {
            if (attribute instanceof Attributes.AttributeConstantValue) {
                return (Attributes.AttributeConstantValue) attribute;
            }
        }
        return null;
    }
}


