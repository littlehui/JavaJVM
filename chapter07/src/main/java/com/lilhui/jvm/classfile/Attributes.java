package com.lilhui.jvm.classfile;

import lombok.Data;

import java.util.Arrays;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/26 13:56
 */
public class Attributes {
    public static Attribute[] newAttributes(BytecodeReader reader, ConstantPoolInfo constantPoolInfo) {
        int attributesCount = reader.readU2();
        Attribute[] attributes = new Attribute[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            int attributeNameIndex = reader.readU2();
            String attributeName = constantPoolInfo.getUtf8(attributeNameIndex);
            attributes[i] = newAttribute(attributeName, constantPoolInfo);
            attributes[i].readInfo(reader);
        }
        return attributes;
    }

    static Attribute newAttribute(String attributeName, ConstantPoolInfo constantPoolInfo) {
        switch (attributeName) {
            case "Code":
                return new AttributeCode(constantPoolInfo);
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

    @Data
    public static class AttributeCode implements Attribute {
        private ConstantPoolInfo constantPoolInfo;
        private int maxStack;
        private int maxLocals;
        private byte[] code;
        private ExceptionTable[] exceptionTable;
        private Attribute[] attributes;

        public AttributeCode(ConstantPoolInfo constantPoolInfo) {
            this.constantPoolInfo = constantPoolInfo;
        }

        @Override
        public void readInfo(BytecodeReader reader) {
            reader.readU4();
            maxStack = reader.readU2();
            maxLocals = reader.readU2();
            int codeLength = reader.readU4();
            code = new byte[codeLength];
            for (int i = 0; i < codeLength; i++) {
                code[i] = reader.readU1();
            }
            int exceptionTableLength = reader.readU2();
            exceptionTable = new ExceptionTable[exceptionTableLength];
            for (int i = 0; i < exceptionTableLength; i++) {
                exceptionTable[i] = new ExceptionTable(
                        reader.readU2(),
                        reader.readU2(),
                        reader.readU2(),
                        reader.readU2()
                );
            }
            int attributesCount = reader.readU2();
            attributes = new Attribute[attributesCount];
            for (int i = 0; i < attributesCount; i++) {
                int attributeNameIndex = reader.readU2();
                String attributeName = constantPoolInfo.getUtf8(attributeNameIndex);
                attributes[i] = Attributes.newAttribute(attributeName, constantPoolInfo);
                attributes[i].readInfo(reader);
            }
        }

        @Override
        public void printInfo() {
            System.out.println("Code:");
            System.out.printf("\tstack=%d, locals=%d\n", maxStack, maxLocals);
            System.out.println("\tcode: " + Arrays.toString(code));
            System.out.println("\texception_table: " + exceptionTable.length);
            for (int index = 0; index < exceptionTable.length; index++) {
                ExceptionTable val = exceptionTable[index];
                System.out.printf("\t#%d{\n", index);
                System.out.println("\tstart_pc: " + val.startPc);
                System.out.println("\tend_pc: " + val.endPc);
                System.out.println("\thandler_pc: " + val.handlerPc);
                System.out.println("\tcatch_type: " + val.catchType);
                System.out.println("\t}");
            }
            System.out.println("attributes: " + attributes.length);
            for (int index = 0; index < attributes.length; index++) {
                Attribute val = attributes[index];
                System.out.printf("\t#%d{\n", index);
                val.printInfo();
                System.out.printf("}\n");
            }
        }
    }

    public static class ExceptionTable {
        int startPc;
        int endPc;
        int handlerPc;
        int catchType;

        public ExceptionTable(int startPc, int endPc, int handlerPc, int catchType) {
            this.startPc = startPc;
            this.endPc = endPc;
            this.handlerPc = handlerPc;
            this.catchType = catchType;
        }
    }

    public static class AttributeExceptions implements Attribute {
        private int[] exceptionIndexTable;

        @Override
        public void readInfo(BytecodeReader reader) {
            reader.readU4();
            int length = reader.readU2();
            exceptionIndexTable = new int[length];
            for (int i = 0; i < length; i++) {
                exceptionIndexTable[i] = reader.readU2();
            }
        }

        @Override
        public void printInfo() {
            System.out.println("Exceptions: " + exceptionIndexTable.length);
            for (int index = 0; index < exceptionIndexTable.length; index++) {
                System.out.printf("#%d\t\t%d\n", index, exceptionIndexTable[index]);
            }
        }
    }

    public static class AttributeDeprecated implements Attribute {
        @Override
        public void readInfo(BytecodeReader reader) {
            reader.readU4();
        }

        @Override
        public void printInfo() {
            // No implementation needed
        }
    }

    public static class AttributeSynthetic implements Attribute {
        @Override
        public void readInfo(BytecodeReader reader) {
            reader.readU4();
        }

        @Override
        public void printInfo() {
            // No implementation needed
        }
    }

    public static class AttributeSourceFile implements Attribute {
        private int sourcefileIndex;

        @Override
        public void readInfo(BytecodeReader reader) {
            reader.readU4();
            sourcefileIndex = reader.readU2();
        }

        @Override
        public void printInfo() {
            System.out.printf("SourceFile:\t\t#%d\n", sourcefileIndex);
        }
    }

    @Data
    public static class AttributeConstantValue implements Attribute {
        private int constantvalueIndex;

        @Override
        public void readInfo(BytecodeReader reader) {
            reader.readU4();
            constantvalueIndex = reader.readU2();
        }

        @Override
        public void printInfo() {
            System.out.printf("ConstantValue:\t\t#%d\n", constantvalueIndex);
        }
    }

    public static class AttributeLineNumber {
        public int startPC;
        public int lineNumber;

        public AttributeLineNumber(int startPC, int lineNumber) {
            this.startPC = startPC;
            this.lineNumber = lineNumber;
        }
    }

    public static class AttributeLineNumberTable implements Attribute {
        private AttributeLineNumber[] lineNumberTable;

        @Override
        public void readInfo(BytecodeReader reader) {
            reader.readU4();
            int length = reader.readU2();
            lineNumberTable = new AttributeLineNumber[length];
            for (int i = 0; i < length; i++) {
                lineNumberTable[i] = new AttributeLineNumber(reader.readU2(), reader.readU2());
            }
        }

        @Override
        public void printInfo() {
            System.out.println("LineNumberTable:");
            for (AttributeLineNumber lineNumber : lineNumberTable) {
                System.out.println("\tstart_pc: " + lineNumber.startPC);
                System.out.println("\tline_number: " + lineNumber.lineNumber);
            }
        }
    }

    public static class AttributeLocalVariable {
        int startPC;
        int length;
        int nameIndex;
        int descriptorIndex;
        int index;

        public AttributeLocalVariable(int startPC, int length, int nameIndex, int descriptorIndex, int index) {
            this.startPC = startPC;
            this.length = length;
            this.nameIndex = nameIndex;
            this.descriptorIndex = descriptorIndex;
            this.index = index;
        }
    }

    public static class AttributeLocalVariableTable implements Attribute {
        private AttributeLocalVariable[] localVariableTable;

        @Override
        public void readInfo(BytecodeReader reader) {
            reader.readU4();
            int length = reader.readU2();
            localVariableTable = new AttributeLocalVariable[length];
            for (int i = 0; i < length; i++) {
                localVariableTable[i] = new AttributeLocalVariable(
                        reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2(), reader.readU2()
                );
            }
        }

        @Override
        public void printInfo() {
            System.out.println("LocalVariableTable:");
            for (AttributeLocalVariable localVariable : localVariableTable) {
                System.out.println("\tstart_pc: " + localVariable.startPC);
                System.out.println("\tlength: " + localVariable.length);
                System.out.println("\tname_index: " + localVariable.nameIndex);
                System.out.println("\tdescriptor_index: " + localVariable.descriptorIndex);
                System.out.println("\tindex: " + localVariable.index);
            }
        }
    }

    public static class AttributeUnparsed implements Attribute {
        @Override
        public void readInfo(BytecodeReader reader) {
            int length = reader.readU4();
            for (int i = 0; i < length; i++) {
                reader.readU1();
            }
        }

        @Override
        public void printInfo() {
            System.out.println("Unparsed");
        }
    }
}



