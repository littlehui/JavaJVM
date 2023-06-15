package com.lilhui.jvm.classpath;

import com.lilhui.jvm.classpath.impl.CompositeEntry;
import com.lilhui.jvm.classpath.impl.DirEntry;
import com.lilhui.jvm.classpath.impl.WildcardEntry;
import com.lilhui.jvm.classpath.impl.ZipEntry;

import java.io.File;
import java.io.IOException;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 16:32
 */
public class EntryUtil {

    public static Entry newEntry(String path) throws IOException {
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }
        if (path.endsWith("*")) {
            return new WildcardEntry(path);
        }
        if (path.endsWith(".jar") || path.endsWith(".JAR") ||
                path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }
        return new DirEntry(path);
    }
}
