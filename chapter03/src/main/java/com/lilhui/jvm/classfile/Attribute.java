package com.lilhui.jvm.classfile;

public interface Attribute {

    void readInfo(BytecodeReader reader);

    void printInfo();
}
