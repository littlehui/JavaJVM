package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 15:49
 */
@Data
public class ConstantIntegerInfo implements ConstantInfo {

    private int value;

    private ConstantPoolInfo constantPoolInfo;

    public ConstantIntegerInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        value = reader.readU4();
    }

    @Override
    public void printInfo() {
        System.out.println("Integer\t\t" + value);
    }
}
