package com.lilhui.jvm.classpath.impl;

import com.lilhui.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 10:09
 */
public class DirEntry implements Entry {

    private String absDir;

    public DirEntry(String path) {
        try {
            String absDir = new File(path).getCanonicalPath();
            this.absDir = absDir;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        String fileName = Paths.get(absDir, className).toString();
        Path filePath = Paths.get(fileName);
        return Files.readAllBytes(filePath);
    }

    @Override
    public String toString() {
        return absDir;
    }
}
