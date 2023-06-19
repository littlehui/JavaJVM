package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 15:50
 */
public class ConstantStringInfo implements ConstantInfo {

    private int stringIndex;

    ConstantPoolInfo constantPoolInfo;

    public ConstantStringInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        stringIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        System.out.printf("String\t\t#%d\t\t%s\n", stringIndex, constantPoolInfo.getUtf8(stringIndex));
    }

    public String getString() {
        return constantPoolInfo.getUtf8(stringIndex);
    }
}