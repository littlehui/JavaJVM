package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 15:51
 */
@Data
public class ConstantDoubleInfo implements ConstantInfo {

    private double value;

    private ConstantPoolInfo constantPoolInfo;

    public ConstantDoubleInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        value = Double.longBitsToDouble(reader.readU8());
    }

    @Override
    public void printInfo() {
        System.out.println("Double\t\t" + value);
    }
}
