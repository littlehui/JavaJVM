package com.lilhui.jvm;

import com.lilhui.jvm.classfile.*;
import com.lilhui.jvm.classpath.ClassPath;
import com.lilhui.jvm.cmd.Cmd;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 17:09
 */
public class Jvm {

    public static void main(String[] args) {
        Cmd cmd = Cmd.parseCmd(args);
        if (cmd.isVersionFlag()) {
            System.out.println("version 0.0.1");
        } else if (cmd.isHelpFlag() || cmd.getClassPath() == null) {
            Cmd.printUsage();
        } else {
            startJVM(cmd);
        }
    }

    public static void startJVM(Cmd cmd) {
        ClassPath cp = ClassPath.parse(cmd.getXjreOption(), cmd.getCpOption());
        String className = cmd.getClassPath().replace(".", "/");
        byte[] bytecode = new byte[0];
        try {
            bytecode = cp.readClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bytecode == null) {
            throw new RuntimeException("找不到类");
        }
        parseBytecode(bytecode);
    }

    public static void parseBytecode(byte[] bytecode) {
        ClassFile classfile = ClassFile.parseBytecode(bytecode);
        printInfo(classfile);
    }

    public static void printInfo(ClassFile classfile) {
        printVersion(classfile);
        printConstantPool(classfile);
        printAccessFlags(classfile);
        printThisClass(classfile);
        printSuperClass(classfile);
        printInterfaces(classfile);
        printFields(classfile);
        printMethods(classfile);
        printAttributes(classfile);
    }


    public static void printVersion(ClassFile classfile) {
        System.out.printf("版本：%d.%d\n", classfile.getMajorVersion(), classfile.getMinorVersion());
    }

    public static void printConstantPool(ClassFile classfile) {
        ConstantPool cp = classfile.getConstantPool();
        System.out.println("Constant pool: " + cp.getSize());
        for (int i = 1; i < cp.getSize(); i++) {
            ConstantInfo constantInfo = cp.getConstantInfo(i);
            if (constantInfo == null) {
                continue;
            }
            System.out.printf("#%d = ", i);
            constantInfo.printInfo();
        }
    }

    public static void printAccessFlags(ClassFile classfile) {
        System.out.println("flags: " + classfile.getAccessFlags());
    }

    public static void printThisClass(ClassFile classfile) {
        ConstantPool cp = classfile.getConstantPool();
        int classIndex = classfile.getThisClass();
        ConstantInfo constantClass = cp.getConstantInfo(classIndex);
        System.out.printf("this_class: #%d\n", classIndex);
        constantClass.printInfo();
    }

    public static void printSuperClass(ClassFile classfile) {
        ConstantPool cp = classfile.getConstantPool();
        int classIndex = classfile.getSuperClass();
        ConstantInfo constantInfo = cp.getConstantInfo(classIndex);
        System.out.printf("super_class: #%d\n", classIndex);
        constantInfo.printInfo();
    }

    public static void printInterfaces(ClassFile classfile) {
        ConstantPool cp = classfile.getConstantPool();
        int[] interfaceIndices = classfile.getInterfaces();
        System.out.println("interfaces: " + interfaceIndices.length);
        for (int i = 0; i < interfaceIndices.length; i++) {
            int interfaceIndex = interfaceIndices[i];
            ConstantInfo constantInfo = cp.getConstantInfo(interfaceIndex);
            System.out.printf("#%d\n", i);
            constantInfo.printInfo();
        }
    }

    public static void printFields(ClassFile classfile) {
        MemberInfo[] fields = classfile.getFields();
        System.out.println("fields: " + fields.length);
        for (int i = 0; i < fields.length; i++) {
            System.out.printf("#%d\n", i);
            fields[i].printInfo();
        }
    }

    public static void printMethods(ClassFile classfile) {
        MemberInfo[] methods = classfile.getMethods();
        System.out.println("methods: " + methods.length);
        for (int i = 0; i < methods.length; i++) {
            System.out.printf("#%d\n", i);
            methods[i].printInfo();
        }
    }

    private static void printAttributes(ClassFile classfile) {
        Attribute[] attributes = classfile.getAttributes();
        System.out.println("attributes: " + attributes.length);
        for (int i = 0; i < attributes.length; i++) {
            System.out.printf("#%d{\n", i);
            attributes[i].printInfo();
            System.out.printf("}\n");
        }
    }
}
