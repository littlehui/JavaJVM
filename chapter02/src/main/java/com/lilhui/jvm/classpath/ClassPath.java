package com.lilhui.jvm.classpath;

import com.lilhui.jvm.classpath.impl.WildcardEntry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 10:10
 */
public class ClassPath implements Entry {

    private Entry bootClasspath;
    private Entry extClasspath;
    private Entry userClasspath;

    public static ClassPath parse(String jreOption, String cpOption) {
        ClassPath cp = new ClassPath();
        cp.parseBootAndExtClasspath(jreOption);
        cp.parseUserClasspath(cpOption);
        return cp;
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        className = className + ".class";
        try {
            return bootClasspath.readClass(className);
        } catch (IOException e) {
            // Ignore and continue searching in the next classpath
        }

        try {
            return extClasspath.readClass(className);
        } catch (IOException e) {
            // Ignore and continue searching in the next classpath
        }
        return userClasspath.readClass(className);
    }

    @Override
    public String toString() {
        return userClasspath.toString();
    }

    private void parseBootAndExtClasspath(String jreOption) {
        String jreDir = getJreDir(jreOption);
        String jreLibPath = Paths.get(jreDir, "lib").toString() + File.separator + "*";
        bootClasspath = new WildcardEntry(jreLibPath);
        String jreExtPath = Paths.get(jreDir, "lib", "ext").toString() + File.separator + "*";
        extClasspath = new WildcardEntry(jreExtPath);
    }

    private String getJreDir(String jreOption) {
        if (jreOption != null && exists(jreOption)) {
            return jreOption;
        }
        if (exists("./jre")) {
            return "./jre";
        }
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome != null) {
            return Paths.get(javaHome, "jre").toString();
        }
        throw new RuntimeException("Can not find jre folder!");
    }

    private boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    private void parseUserClasspath(String cpOption) {
        if (cpOption == null || cpOption.isEmpty()) {
            cpOption = ".";
        }
        try {
            userClasspath = EntryUtil.newEntry(cpOption);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
