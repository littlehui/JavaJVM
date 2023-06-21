package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;
import lombok.Getter;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/21 21:31
 */
public class ConstantInvokeDynamicInfo implements ConstantInfo {

    @Getter
    private int bootStrapMethodAttrIndex;

    @Getter
    private int nameAndTypeIndex;

    private ConstantPoolInfo constantPoolInfo;

    public ConstantInvokeDynamicInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        this.bootStrapMethodAttrIndex = reader.readU2();
        this.nameAndTypeIndex = reader.readU2();
    }

    @Override
    public void printInfo() {

    }
}
