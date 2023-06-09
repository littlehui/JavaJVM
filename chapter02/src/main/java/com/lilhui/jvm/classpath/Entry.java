package com.lilhui.jvm.classpath;

import java.io.IOException;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 10:08
 */
public interface Entry {

    public byte[] readClass(String className) throws Exception;

}
