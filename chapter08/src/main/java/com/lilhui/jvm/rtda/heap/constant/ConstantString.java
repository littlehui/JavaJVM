package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.rtda.heap.Constant;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 16:36
 */
public class ConstantString implements Constant {

    private String value;

    public ConstantString(String value) {
        this.value = value;
    }

    @Override
    public <T> void setValue(T value) {
        this.value = (String)value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
