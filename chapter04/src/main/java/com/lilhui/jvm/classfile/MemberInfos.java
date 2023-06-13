package com.lilhui.jvm.classfile;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 14:55
 */
public class MemberInfos {

    public static Field[] newFields(BytecodeReader reader, ConstantPool constantPool) {
        short fileCount = reader.readU2();
        Field[] fields = new Field[fileCount];
        for (int i=0; i<fileCount; i++) {
            fields[i] = new Field(constantPool);
            fields[i].readInfo(reader);
        }
        return fields;
    }

    public static  Method[] newMethods(BytecodeReader reader, ConstantPool constantPool) {
        short methodCount = reader.readU2();
        Method[] methods = new Method[methodCount];
        for (int i=0; i<methodCount; i++) {
            methods[i] = new Method(constantPool);
            methods[i].readInfo(reader);
        }
        return methods;
    }
}
