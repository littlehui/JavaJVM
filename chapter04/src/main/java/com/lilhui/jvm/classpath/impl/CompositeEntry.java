package com.lilhui.jvm.classpath.impl;

import com.lilhui.jvm.classpath.Entry;
import com.lilhui.jvm.classpath.EntryUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 10:09
 */
public class CompositeEntry implements Entry {

    private List<Entry> entries;

    String pathListSeparator = System.getProperty("path.separator");

    public CompositeEntry(String pathList) {
        String[] paths = pathList.split(pathListSeparator);
        entries = new ArrayList<>();
        for (String path : paths) {
            Entry entry = null;
            try {
                entry = EntryUtil.newEntry(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            entries.add(entry);
        }
    }

    @Override
    public byte[] readClass(String className) throws Exception {
        for (Entry entry : entries) {
            try {
                byte[] data = entry.readClass(className);
                return data;
            } catch (IOException e) {
                // Ignore and continue searching in the next entry
            }
        }
        throw new IOException("Class not found: " + className);
    }

    @Override
    public String toString() {
        List<String> entryStrings = new ArrayList<>();
        for (Entry entry : entries) {
            entryStrings.add(entry.toString());
        }
        return String.join(pathListSeparator, entryStrings);
    }
}
