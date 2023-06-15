package com.lilhui.jvm.classfile;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 14:55
 */
public class MemberInfos {

    public static FieldInfo[] newFields(BytecodeReader reader, ConstantPoolInfo constantPoolInfo) {
        short fileCount = reader.readU2();
        FieldInfo[] fields = new FieldInfo[fileCount];
        for (int i=0; i<fileCount; i++) {
            fields[i] = new FieldInfo(constantPoolInfo);
            fields[i].readInfo(reader);
        }
        return fields;
    }

    public static  MethodInfo[] newMethods(BytecodeReader reader, ConstantPoolInfo constantPoolInfo) {
        short methodCount = reader.readU2();
        MethodInfo[] methods = new MethodInfo[methodCount];
        for (int i=0; i<methodCount; i++) {
            methods[i] = new MethodInfo(constantPoolInfo);
            methods[i].readInfo(reader);
        }
        return methods;
    }


}
