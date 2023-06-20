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
public class ConstantUtf8Info implements ConstantInfo {

    @Getter
    private String value;

    private ConstantPoolInfo constantPoolInfo;

    public ConstantUtf8Info(ConstantPoolInfo constantPoolInfo) {
        this.constantPoolInfo = constantPoolInfo;
    }

    @Override
    public void readInfo(BytecodeReader reader) {
        int length = reader.readU2();
        byte[] bytes = reader.readBytes(length);
        value = new String(bytes);
    }

    @Override
    public void printInfo() {
        System.out.println("Utf8\t\t" + value);
    }
}
