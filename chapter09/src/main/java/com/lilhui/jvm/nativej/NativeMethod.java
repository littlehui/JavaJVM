package com.lilhui.jvm.nativej;

import com.lilhui.jvm.rtda.Frame;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/27 16:44
 */
public interface NativeMethod {

    public void invoke(Frame frame) throws Exception;
}
