package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;
import lombok.Getter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 15:52
 */
public class ConstantNameAndTypeInfo implements ConstantInfo {

    @Getter
    private int nameIndex;

    @Getter
    private int descriptorIndex;

    private ConstantPoolInfo constantPoolInfo;

    public ConstantNameAndTypeInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        nameIndex = reader.readU2();
        descriptorIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        try {
            System.out.printf("NameAndType\t\t#%d:%d\t\t%s:%s\n",
                    nameIndex, descriptorIndex,
                    constantPoolInfo.getUtf8(nameIndex),
                    constantPoolInfo.getUtf8(descriptorIndex));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}