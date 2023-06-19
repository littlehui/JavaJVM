package com.lilhui.jvm.classfile;

import com.lilhui.jvm.classfile.constant.ConstantClassInfo;
import lombok.Data;

import java.util.Arrays;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/26 13:41
 */

@Data
public class ClassFile {
    private int minorVersion;
    private int majorVersion;
    private ConstantPoolInfo constantPoolInfo;
    private int accessFlags;
    private int thisClass;
    private int superClass;
    private int[] interfaces;
    private FieldInfo[] fields;
    private MethodInfo[] methods;
    private Attribute[] attributes;

    public ClassFile() {

    }

    public ClassFile(int minorVersion, int majorVersion, ConstantPoolInfo constantPoolInfo, int accessFlags, int thisClass, int superClass,
                     int[] interfaces, FieldInfo[] fields, MethodInfo[] methods, Attribute[] attributes) {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
        this.constantPoolInfo = constantPoolInfo;
        this.accessFlags = accessFlags;
        this.thisClass = thisClass;
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.fields = fields;
        this.methods = methods;
        this.attributes = attributes;
    }
    private void parseAndCheckMagic(BytecodeReader reader) {
        int magic = reader.readU4();
        if (magic != 0xCAFEBABE) {
            throw new RuntimeException("Invalid bytecode format: magic!");
        }
    }

    private void parseAndCheckVersion(BytecodeReader reader) {
        minorVersion = reader.readU2();
        majorVersion = reader.readU2();

        switch (majorVersion) {
            case 45:
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                if (minorVersion == 0) {
                    return;
                }
        }
        throw new RuntimeException("Unsupported bytecode version");
    }

    private void parseConstantPool(BytecodeReader reader) {
        constantPoolInfo = new ConstantPoolInfo(reader);
    }

    private void parseAccessFlags(BytecodeReader reader) {
        accessFlags = reader.readU2();
    }

    private void parseThisClass(BytecodeReader reader) {
        thisClass = reader.readU2();
    }

    private void parseSuperClass(BytecodeReader reader) {
        superClass = reader.readU2();
    }

    private void parseInterfaces(BytecodeReader reader) {
        int interfacesCount = reader.readU2();
        interfaces = new int[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            interfaces[i] = reader.readU2();
        }
    }

    private void parseFields(BytecodeReader reader) {
        fields = MemberInfos.newFields(reader, constantPoolInfo);
    }

    private void parseMethods(BytecodeReader reader) {
        methods = MemberInfos.newMethods(reader, constantPoolInfo);
    }

    private void parseAttributes(BytecodeReader reader) {
        attributes = Attributes.newAttributes(reader, constantPoolInfo);
    }

    @Override
    public String toString() {
        return "ClassFile{" +
                "minorVersion=" + minorVersion +
                ", majorVersion=" + majorVersion +
                ", constantPool=" + constantPoolInfo +
                ", accessFlags=" + accessFlags +
                ", thisClass=" + thisClass +
                ", superClass=" + superClass +
                ", interfaces=" + Arrays.toString(interfaces) +
                ", fields=" + Arrays.toString(fields) +
                ", methods=" + Arrays.toString(methods) +
                ", attributes=" + Arrays.toString(attributes) +
                '}';
    }

    public static ClassFile parseBytecode(byte[] bytecode) {
        BytecodeReader reader = new BytecodeReader(bytecode);
        ClassFile classFile = new ClassFile();

        classFile.parseAndCheckMagic(reader);
        classFile.parseAndCheckVersion(reader);
        classFile.parseConstantPool(reader);
        classFile.parseAccessFlags(reader);
        classFile.parseThisClass(reader);
        classFile.parseSuperClass(reader);
        classFile.parseInterfaces(reader);
        classFile.parseFields(reader);
        classFile.parseMethods(reader);
        classFile.parseAttributes(reader);

        return classFile;
    }

    public String getClassName() {
        return constantPoolInfo.getClassName(thisClass);
    }

    public String getSuperClassName() {
        ConstantClassInfo classInfo = constantPoolInfo.getConstantInfo(superClass, ConstantClassInfo.class);
        if (classInfo != null) {
            return constantPoolInfo.getUtf8(classInfo.getNameIndex());
        } else {
            return null;
        }
    }

    public String[] getInterfaceNames() {
        String[] interfaceNames = new String[interfaces.length];
        for (int i=0; i < interfaces.length; i++) {
            String interfaceName = constantPoolInfo.getUtf8(constantPoolInfo.getConstantInfo(interfaces[i], ConstantClassInfo.class).getNameIndex());
            interfaceNames[i] = interfaceName;
        }
        return interfaceNames;
    }
}
