package com.lilhui.jvm.classpath.impl;

import com.lilhui.jvm.classpath.Entry;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 10:09
 */
public class ZipEntry implements Entry {

    private String absPath;

    public ZipEntry(String path) {
        try {
            String absPath = new File(path).getCanonicalPath();
            this.absPath = absPath;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        try (ZipFile zipFile = new ZipFile(absPath)) {
            java.util.zip.ZipEntry entry = zipFile.getEntry(className);
            if (entry != null) {
                try (InputStream inputStream = zipFile.getInputStream(entry)) {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        byteStream.write(buffer, 0, bytesRead);
                    }
                    return byteStream.toByteArray();
                }
            }
        }
        throw new IOException("Class not found: " + className);
    }

    @Override
    public String toString() {
        return absPath;
    }
}
