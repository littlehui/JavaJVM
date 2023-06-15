package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.rtda.heap.Constant;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 16:29
 */
public class ConstantLong implements Constant {

    long value;

    public ConstantLong(Long value) {
        this.value = value;
    }
    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public <T> void setValue(T value) {
        this.value = (Long)value;
    }
}
