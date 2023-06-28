package com.lilhui.jvm.rtda.heap;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 16:11
 */
public class ClazzHelper {

    private static Map<String, String> primitiveTypes = new HashMap<>();

    static {
        primitiveTypes.put("void", "V");
        primitiveTypes.put("boolean", "Z");
        primitiveTypes.put("byte", "B");
        primitiveTypes.put("short", "S");
        primitiveTypes.put("int", "I");
        primitiveTypes.put("long", "J");
        primitiveTypes.put("char", "C");
        primitiveTypes.put("float", "F");
        primitiveTypes.put("double", "D");
    }

    public static String arrayClazzName(String clazzName) {
        return "[" + toDescriptor(clazzName);
    }

    private static String toDescriptor(String clazzName) {
        if (clazzName.charAt(0) == '[') {
            return clazzName;
        }
        return primitiveTypes.get(clazzName);
    }

    public static Clazz toArrayClazz(Clazz clazz) {
        String arrayClazzName = arrayClazzName(clazz.getName());
        return clazz.getLoader().loadClass(arrayClazzName);
    }

    public static String getComponentClazzName(String name) {
        if (name.charAt(0) == '[') {
            String componentTypeDescriptor = name.substring(1);
            return toClazzName(componentTypeDescriptor);
        }
        throw new RuntimeException();
    }

    private static String toClazzName(String componentTypeDescriptor) {
        if (componentTypeDescriptor.charAt(0) == '[') {
            return componentTypeDescriptor;
        }
        if (componentTypeDescriptor.charAt(0) == 'L') {
            return componentTypeDescriptor.substring(1, componentTypeDescriptor.length() - 1);
        }
        String className = primitiveTypes.get(componentTypeDescriptor);
        if (className == null) {
            throw new RuntimeException("no class name:" + componentTypeDescriptor);
        }
        return className;
    }
}
