package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 15:53
 */
public class ConstantInterfaceMethodrefInfo implements ConstantInfo {
    private int classIndex;
    private int nameAndTypeIndex;
    private ConstantPoolInfo constantPoolInfo;

    public ConstantInterfaceMethodrefInfo(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }
    @Override
    public void readInfo(BytecodeReader reader) {
        classIndex = reader.readU2();
        nameAndTypeIndex = reader.readU2();
    }

    @Override
    public void printInfo() {
        String className = constantPoolInfo.getClassName(classIndex);
        String nameAndType = constantPoolInfo.getNameAndType(nameAndTypeIndex);
        System.out.printf("InterfaceMethodref\t\t#%d:#%d\t\t%s\n",
                classIndex, nameAndTypeIndex, className + "." + nameAndType);
    }

    public String getClassName() {
        return constantPoolInfo.getClassName(classIndex);
    }

    public String getNameAndType() {
        return constantPoolInfo.getNameAndType(nameAndTypeIndex);
    }
}
