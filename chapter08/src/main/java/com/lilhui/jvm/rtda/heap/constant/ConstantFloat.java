package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.rtda.heap.Constant;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 16:31
 */
public class ConstantFloat implements Constant {

    float value;

    public ConstantFloat(Float value) {
        this.value = value;
    }

    @Override
    public <T> void setValue(T value) {
        this.value = (Float) value;
    }

    @Override
    public Float getValue() {
        return (Float)value;
    }
}
