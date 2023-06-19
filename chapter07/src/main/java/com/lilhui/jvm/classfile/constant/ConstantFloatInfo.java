package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 15:52
 */
@Data
public class ConstantFloatInfo implements ConstantInfo {

    private float value;

    private ConstantPoolInfo constantPoolInfo;

    public ConstantFloatInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        value = Float.intBitsToFloat(reader.readU4());
    }

    @Override
    public void printInfo() {
        System.out.println("Float\t\t" + value);
    }
}