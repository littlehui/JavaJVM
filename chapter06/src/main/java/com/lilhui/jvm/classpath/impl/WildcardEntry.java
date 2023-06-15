package com.lilhui.jvm.classpath.impl;

import com.lilhui.jvm.classpath.Entry;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 10:09
 */
public class WildcardEntry implements Entry {

    private final List<Entry> entries;

    public WildcardEntry(String path) {
        String baseDir = path.substring(0, path.length() - 1); // remove *
        try (Stream<Path> walk = Files.walk(Paths.get(baseDir))) {
            entries = walk.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(p -> p.endsWith(".jar") || p.endsWith(".JAR"))
                    .map(ZipEntry::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        for (Entry entry : entries) {
            try {
                return entry.readClass(className);
            } catch (Exception ignored) {
            }
        }
        throw new Exception("class not found: " + className);
    }

    @Override
    public String toString() {
        return entries.stream()
                .map(Entry::toString)
                .collect(Collectors.joining(File.pathSeparator));
    }
}
