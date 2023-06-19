package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.classfile.constant.ConstantDoubleInfo;
import com.lilhui.jvm.rtda.heap.Constant;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 16:35
 */
public class ConstantDouble implements Constant {

    private double value;

    public ConstantDouble(Double value) {
        this.value = value;
    }

    @Override
    public <T> void setValue(T value) {
        this.value = (Double) value;
    }

    @Override
    public Double getValue() {
        return value;
    }
}
