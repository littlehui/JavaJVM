package com.lilhui.jvm.classfile;

import lombok.Getter;

public class ConstantInfoFactory {
    public static ConstantInfo newConstantInfo(BytecodeReader bytecodeReader, ConstantPool constantPool) {
        byte tag = bytecodeReader.readU1();
        switch (tag) {
            case ConstantPoolTags.CONSTANT_INTEGER:
                return new ConstantInteger(constantPool);
            case ConstantPoolTags.CONSTANT_FLOAT:
                return new ConstantFloat(constantPool);
            case ConstantPoolTags.CONSTANT_LONG:
                return new ConstantLong(constantPool);
            case ConstantPoolTags.CONSTANT_DOUBLE:
                return new ConstantDouble(constantPool);
            case ConstantPoolTags.CONSTANT_UTF_8:
                return new ConstantUtf8(constantPool);
            case ConstantPoolTags.CONSTANT_STRING:
                return new ConstantString(constantPool);
            case ConstantPoolTags.CONSTANT_CLASS:
                return new ConstantClass(constantPool);
            case ConstantPoolTags.CONSTANT_FIELD_REF:
                return new ConstantFieldref(constantPool);
            case ConstantPoolTags.CONSTANT_METHOD_REF:
                return new ConstantMethodref(constantPool);
            case ConstantPoolTags.CONSTANT_INTERFACE_METHOD_REF:
                return new ConstantInterfaceMethodref(constantPool);
            case ConstantPoolTags.CONSTANT_NAME_AND_TYPE:
                return new ConstantNameAndType(constantPool);
            default:
                throw new RuntimeException("Unknown constant pool tag: " + tag);
        }
    }
}

class ConstantInteger implements ConstantInfo {

    private int value;

    private ConstantPool constantPool;

    public ConstantInteger(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        value = reader.readU4();
    }

    @Override
    public void printInfo() {
        System.out.println("Integer\t\t" + value);
    }
}

class ConstantLong implements ConstantInfo {

    private long value;

    private ConstantPool constantPool;

    ConstantLong(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        value = reader.readU8();
    }

    @Override
    public void printInfo() {
        System.out.println("Long\t\t" + value);
    }
}

class ConstantFloat implements ConstantInfo {

    private float value;

    private ConstantPool constantPool;

    public ConstantFloat(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        value = Float.intBitsToFloat(reader.readU4());
    }

    @Override
    public void printInfo() {
        System.out.println("Float\t\t" + value);
    }
}

class ConstantDouble implements ConstantInfo {

    private double value;

    private ConstantPool constantPool;

    public ConstantDouble(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        value = Double.longBitsToDouble(reader.readU8());
    }

    @Override
    public void printInfo() {
        System.out.println("Double\t\t" + value);
    }
}

class ConstantUtf8 implements ConstantInfo {

    @Getter
    private String value;

    private ConstantPool constantPool;

    public ConstantUtf8(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        int length = reader.readU2();
        byte[] bytes = reader.readBytes(length);
        value = new String(bytes);
    }

    @Override
    public void printInfo() {
        System.out.println("Utf8\t\t" + value);
    }
}

class ConstantString implements ConstantInfo {

    private int stringIndex;

    ConstantPool constantPool;

    public ConstantString(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        stringIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        System.out.printf("String\t\t#%d\t\t%s\n", stringIndex, constantPool.getUtf8(stringIndex));
    }
}

class ConstantClass implements ConstantInfo {

    @Getter
    private int nameIndex;

    private ConstantPool constantPool;

    public ConstantClass(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        nameIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        try {
            System.out.printf("Class\t\t#%d\t\t%s\n", nameIndex, constantPool.getUtf8(nameIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ConstantNameAndType implements ConstantInfo {

    @Getter
    private int nameIndex;

    @Getter
    private int descriptorIndex;

    private ConstantPool constantPool;

    public ConstantNameAndType(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        nameIndex = reader.readU2();
        descriptorIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        try {
            System.out.printf("NameAndType\t\t#%d:%d\t\t%s:%s\n",
                    nameIndex, descriptorIndex,
                    constantPool.getUtf8(nameIndex),
                    constantPool.getUtf8(descriptorIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ConstantFieldref implements ConstantInfo {
    private int classIndex;
    private int nameAndTypeIndex;

    private ConstantPool constantPool;

    public ConstantFieldref(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        classIndex = reader.readU2();
        nameAndTypeIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        String className = constantPool.getClassName(classIndex);
        String nameAndType = constantPool.getNameAndType(nameAndTypeIndex);
        System.out.printf("Fieldref\t\t#%d:#%d\t\t%s\n",
                classIndex, nameAndTypeIndex, className + "." + nameAndType);
    }
}

class ConstantMethodref implements ConstantInfo {
    private int classIndex;
    private int nameAndTypeIndex;

    private ConstantPool constantPool;

    ConstantMethodref(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        classIndex = reader.readU2();
        nameAndTypeIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        String className = constantPool.getClassName(classIndex);
        String nameAndType = constantPool.getNameAndType(nameAndTypeIndex);
        System.out.printf("Methodref\t\t#%d:#%d\t\t%s\n",
                classIndex, nameAndTypeIndex, className + "." + nameAndType);
    }
}

class ConstantInterfaceMethodref implements ConstantInfo {
    private int classIndex;
    private int nameAndTypeIndex;
    private ConstantPool constantPool;

    public ConstantInterfaceMethodref(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }
    @Override
    public void readInfo(BytecodeReader reader) {
        classIndex = reader.readU2();
        nameAndTypeIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        String className = constantPool.getClassName(classIndex);
        String nameAndType = constantPool.getNameAndType(nameAndTypeIndex);
        System.out.printf("InterfaceMethodref\t\t#%d:#%d\t\t%s\n",
                classIndex, nameAndTypeIndex, className + "." + nameAndType);
    }
}

