package com.lilhui.jvm.classfile;

import com.lilhui.jvm.classpath.ClassPath;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.Constant;
import com.lilhui.jvm.rtda.heap.Field;
import com.lilhui.jvm.rtda.heap.Slots;

import java.util.HashMap;
import java.util.Map;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 13:51
 */
public class ClassLoader {

    private ClassPath classPath;
    private Map<String, Clazz> classMap;
    private boolean verboseFlag;

    public ClassLoader(ClassPath classPath, boolean verboseFlag) {
        this.classPath = classPath;
        this.classMap = new HashMap<>();
        this.verboseFlag = verboseFlag;
    }

    public Clazz loadClass(String className) {
        if (classMap.containsKey(className)) {
            return classMap.get(className);
        }
        return loadNonArrayClass(className);
    }

    private Clazz loadNonArrayClass(String className) {
        Clazz clazz = defineClass(className);
        link(clazz);
        if (verboseFlag) {
            System.out.printf("[Loaded %s]\n", className);
        }
        return clazz;
    }

    private Clazz defineClass(String className) {
        byte[] bytecode = getBytecode(className);
        ClassFile classFile = parseClass(bytecode);
        Clazz clazz = new Clazz(classFile);
        clazz.setLoader(this);
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        classMap.put(className, clazz);
        return clazz;
    }

    private byte[] getBytecode(String className) {
        byte[] bytecode = new byte[0];
        try {
            bytecode = classPath.readClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bytecode == null) {
            throw new RuntimeException("java.lang.ClassNotFoundException: " + className);
        }
        return bytecode;
    }

    private ClassFile parseClass(byte[] bytecode) {
        return ClassFile.parseBytecode(bytecode);
    }

    private void resolveSuperClass(Clazz clazz) {
        String superClassName = clazz.getSuperClassName();
        if (superClassName != null && !superClassName.equals("")) {
            clazz.setSuperClazz(loadClass(superClassName));
        }
    }

    private void resolveInterfaces(Clazz clazz) {
        String[] interfaceNames = clazz.getInterfaceNames();
        int interfaceCount = interfaceNames.length;
        if (interfaceCount > 0) {
            Clazz[] interfaces = new Clazz[interfaceCount];
            for (int i = 0; i < interfaceCount; i++) {
                interfaces[i] = loadClass(interfaceNames[i]);
            }
            clazz.setInterfaces(interfaces);
        }
    }

    private void link(Clazz clazz) {
        verify(clazz);
        prepare(clazz);
    }

    private void verify(Clazz clazz) {
        // Perform verification logic here
    }

    private void prepare(Clazz clazz) {
        this.calculateInstanceFieldSlotIds(clazz);
        this.calculateStaticFieldSlotIds(clazz);
        this.allocateAndInitStaticVars(clazz);
    }

    private void allocateAndInitStaticVars(Clazz clazz) {
        clazz.setStaticVars(new Slots(clazz.getStaticSlotCount()));
        for (Field field : clazz.getFields()) {
            if (field.isStatic() && field.isFinal()) {
                //静态，或者final的进行初始化
                initStaticFinalVar(clazz, field);
            }
        }
    }

    private void initStaticFinalVar(Clazz clazz, Field field) {
        int index = field.getSlotId();
        Slots staticVars = clazz.getStaticVars();
        Constant constant = clazz.getConstantPool().getConstant(field.getConstantValueIndex());
        switch (field.getDescriptor()) {
            case "Z":
            case "B":
            case "C":
            case "S":
            case "I":
                staticVars.setInt(index, constant.getValue());
            case "J":
                staticVars.setLong(index, constant.getValue());
            case "F":
                staticVars.setFloat(index, constant.getValue());
            case "D":
                staticVars.setDouble(index, constant.getValue());
            case "Ljava/lang/String;":
                throw new RuntimeException("还未实现的静态类型。String");
        }
    }

    private void calculateStaticFieldSlotIds(Clazz clazz) {
        int slotId = 0;
        for (Field field : clazz.getFields()) {
            if (field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setStaticSlotCount(slotId);
    }

    private void calculateInstanceFieldSlotIds(Clazz clazz) {
        int slotId = 0;
        if (clazz.getSuperClazz() != null) {
            slotId = clazz.getSuperClazz().getInstanceSlotCount();
        }
        for (Field field : clazz.getFields()) {
            if (!field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setInstanceSlotCount(slotId);
    }
}
