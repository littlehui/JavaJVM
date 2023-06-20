package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.rtda.heap.Constant;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 16:10
 */
public class ConstantInteger implements Constant {

    private Integer value;

    public ConstantInteger(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public <T> void setValue(T value) {
        this.value = (Integer) value;
    }
}
