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
public class ConstantLongInfo implements ConstantInfo {

    private long value;

    private ConstantPoolInfo constantPoolInfo;

    ConstantLongInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        value = reader.readU8();
    }

    @Override
    public void printInfo() {
        System.out.println("Long\t\t" + value);
    }
}