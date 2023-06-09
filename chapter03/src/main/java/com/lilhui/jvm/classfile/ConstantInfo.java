package com.lilhui.jvm.classfile;

public interface ConstantInfo {

    void readInfo(BytecodeReader reader);
    void printInfo();
}
