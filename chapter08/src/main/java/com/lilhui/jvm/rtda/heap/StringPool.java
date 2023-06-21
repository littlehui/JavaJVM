package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.ClazzLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/21 20:33
 */
public class StringPool {

    private static Map<String, Object> internedStrings = new HashMap<>();

    // *******************************************************
    // string转jvm String
    public static Object stringToJvmString(ClazzLoader loader, String str) {
        if (internedStrings.containsKey(str)) {
            return internedStrings.get(str);
        }
        char[] chars = str.toCharArray();
        Object jChars = new Object(loader.loadClass("[C"), chars);
        Object jvmStr = loader.loadClass("java/lang/String").newObject();
        jvmStr.setRefChar("value", "[C", jChars);
        internedStrings.put(str, jvmStr);
        return jvmStr;
    }

    // *******************************************************
    // java String转string
    public static String jvmStrToString(Object jStr) {
        Object jChars = jStr.getRefChar("value", "[C");
        char[] chars = (char[]) jChars.getData();
        return new String(chars);
    }
}

