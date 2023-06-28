package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;
import lombok.Getter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 15:51
 */
public class ConstantClassInfo implements ConstantInfo {

    @Getter
    private int nameIndex;

    private ConstantPoolInfo constantPoolInfo;

    public ConstantClassInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        nameIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        try {
            System.out.printf("Class\t\t#%d\t\t%s\n", nameIndex, constantPoolInfo.getUtf8(nameIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClassName() {
        return constantPoolInfo.getUtf8(nameIndex);
    }
}