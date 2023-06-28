package com.lilhui.jvm.nativej;

import java.util.HashMap;
import java.util.Map;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/27 16:43
 */
public class Registry {

    private static Map<String, NativeMethod> nativeMethodMap = new HashMap();

    public static void register(String clazzName, String methodName, String methodDescriptor, NativeMethod method) {
        String key = clazzName + "~" + methodName + "~" + methodDescriptor;
        nativeMethodMap.put(key, method);
    }

    public static NativeMethod findNativeMethod(String clazzName, String methodName, String methodDescriptor) {
        String key = clazzName + "~" + methodName + "~" + methodDescriptor;
        NativeMethod nativeMethod = nativeMethodMap.get(key);
        if (nativeMethod != null) {
            return nativeMethod;
        }
        if (methodDescriptor.equals("()V") && methodName.equals("registerNatives")) {
            return new EmptyNativeMethod();
        }
        return null;
    }

}
