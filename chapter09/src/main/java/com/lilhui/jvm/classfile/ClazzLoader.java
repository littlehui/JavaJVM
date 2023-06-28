package com.lilhui.jvm.classfile;

import com.lilhui.jvm.classpath.ClassPath;
import com.lilhui.jvm.rtda.heap.Object;
import com.lilhui.jvm.rtda.heap.*;

import java.util.HashMap;
import java.util.Map;

import static com.lilhui.jvm.rtda.heap.AccessFlags.ACC_PUBLIC;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 13:51
 */
public class ClazzLoader {

    private ClassPath classPath;
    private Map<String, Clazz> classMap;
    private boolean verboseFlag;

    private static Map<String, String> primitiveTypes = new HashMap<>();

    static {
        primitiveTypes.put("void","V");
        primitiveTypes.put("boolean","Z");
        primitiveTypes.put("byte","B");
        primitiveTypes.put("short","S");
        primitiveTypes.put("int","I");
        primitiveTypes.put("long","J");
        primitiveTypes.put("char","C");
        primitiveTypes.put("float","F");
        primitiveTypes.put("double","D");
    }


    public ClazzLoader(ClassPath classPath, boolean verboseFlag) {
        this.classPath = classPath;
        this.classMap = new HashMap<>();
        this.verboseFlag = verboseFlag;
        this.loadBasicClazzes();
        this.loadPrimitiveClazzes();
    }

    private void loadPrimitiveClazzes() {
        Clazz jlClassClass = classMap.get("java/lang/Class");
        for (String primitiveType : primitiveTypes.keySet()) {
            Clazz classobj = new Clazz();
            classobj.setAccessFlags(ACC_PUBLIC);
            classobj.setName(primitiveType);
            classobj.setLoader(this);
            classobj.setInitializationFlag(true);
            classobj.setJClazz(jlClassClass.newObject());
            classobj.getJClazz().setExtra(classobj);
            classMap.put(primitiveType, classobj);
        }
    }


    private void loadBasicClazzes() {
        Clazz objClazz = loadClass("java/lang/Class");
        for (Clazz clazz : classMap.values()) {
            if (clazz.getJClazz() == null) {
                clazz.setJClazz(objClazz.newObject());
                clazz.getJClazz().setExtra(clazz);
            }
        }
    }

    public Clazz loadClass(String className) {
        if (classMap.containsKey(className)) {
            return classMap.get(className);
        }
        Clazz clazz;
        if (className.charAt(0) == '[') {
            clazz = this.loadArrayClazz(className);
        } else {
            clazz = loadNonArrayClazz(className);
        }
        Clazz jlClassClass = classMap.get("java/lang/Class");
        if (jlClassClass != null) {
            clazz.setJClazz(jlClassClass.newObject());
            clazz.getJClazz().setExtra(clazz);
        }
        return clazz;
    }

    private Clazz loadArrayClazz(String className) {
        Clazz clazz = defineArrayClazz(className);
        if (verboseFlag) {
            System.out.printf("[Loaded %s]\n", className);
        }
        return clazz;
    }

    private Clazz loadNonArrayClazz(String className) {
        Clazz clazz = defineClazz(className);
        link(clazz);
        if (verboseFlag) {
            System.out.printf("[Loaded %s]\n", className);
        }
        return clazz;
    }

    private Clazz defineArrayClazz(String className) {
        Clazz clazz = new ArrayClazz();
        clazz.setLoader(this);
        clazz.setAccessFlags(ACC_PUBLIC);
        clazz.setName(className);
        clazz.setInitializationFlag(true);
        clazz.setSuperClazz(this.loadClass("java/lang/Object"));
        clazz.setSuperClassName("java/lang/Object");
        Clazz[] interfaceClazzs = new Clazz[] {
                this.loadClass("java/lang/Cloneable"),
                this.loadClass("java/io/Serializable")
        };
        clazz.setInterfaces(interfaceClazzs);
        String[] interfaceClazzsNames = new String[] {
                "java/lang/Cloneable",
               "java/io/Serializable"
        };
        clazz.setInterfaceNames(interfaceClazzsNames);
        this.classMap.put(className, clazz);
        return clazz;
    }

    private Clazz defineClazz(String className) {
        byte[] bytecode = getBytecode(className);
        ClassFile classFile = parseClazz(bytecode);
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

    private ClassFile parseClazz(byte[] bytecode) {
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
                break;
            case "J":
                staticVars.setLong(index, constant.getValue());
                break;
            case "F":
                staticVars.setFloat(index, constant.getValue());
                break;
            case "D":
                staticVars.setDouble(index, constant.getValue());
                break;
            case "Ljava/lang/String;":
                String str = constant.getValue();
                Object jvmStr = StringPool.stringToJvmString(clazz.getLoader(), str);
                staticVars.setRef(index, jvmStr);
                break;
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
