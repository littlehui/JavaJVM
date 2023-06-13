package com.lilhui.jvm.classfile;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/26 13:51
 */
public class MemberInfo {
    ConstantPool constantPool;
    int accessFlags;
    int nameIndex;
    int descriptorIndex;
    Attribute[] attributes;

    public MemberInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public void readInfo(BytecodeReader reader) {
        accessFlags = reader.readU2();
        nameIndex = reader.readU2();
        descriptorIndex = reader.readU2();
        int attributesCount = reader.readU2();
        attributes = new Attribute[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            int attributeNameIndex = reader.readU2();
            String attributeName = constantPool.getUtf8(attributeNameIndex);
            attributes[i] = newAttribute(attributeName, constantPool);
            attributes[i].readInfo(reader);
        }
    }

    public void printInfo() {
        System.out.println("access_flags: " + accessFlags);
        System.out.println("name_index: #" + nameIndex + "\t\t" + constantPool.getUtf8(nameIndex));
        System.out.println("descriptor_index: #" + descriptorIndex + "\t\t" + constantPool.getUtf8(descriptorIndex));
        System.out.println("attributes: #" + attributes.length);
        for (int index = 0; index < attributes.length; index++) {
            System.out.print("#" + index + "{");
            attributes[index].printInfo();
            System.out.println("}");
        }
    }

    private Attribute newAttribute(String attributeName, ConstantPool constantPool) {
        switch (attributeName) {
            case "Code":
                return new AttributeCode(constantPool);
            case "Deprecated":
                return new AttributeDeprecated();
            case "ConstantValue":
                return new AttributeConstantValue();
            case "Exceptions":
                return new AttributeExceptions();
            case "LineNumberTable":
                return new AttributeLineNumberTable();
            case "LocalVariableTable":
                return new AttributeLocalVariableTable();
            case "SourceFile":
                return new AttributeSourceFile();
            default:
                return new AttributeUnparsed();
        }
    }
}


