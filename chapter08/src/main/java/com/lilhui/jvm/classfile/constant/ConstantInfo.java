package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;

public interface ConstantInfo {

    void readInfo(BytecodeReader reader);

    void printInfo();
}
