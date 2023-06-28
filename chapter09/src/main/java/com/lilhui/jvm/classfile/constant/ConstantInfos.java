package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import com.lilhui.jvm.classfile.ConstantPoolInfo;
import com.lilhui.jvm.classfile.ConstantPoolTags;

public class ConstantInfos {

    public static ConstantInfo newConstantInfo(BytecodeReader bytecodeReader, ConstantPoolInfo constantPoolInfo) {
        byte tag = bytecodeReader.readU1();
        switch (tag) {
            case ConstantPoolTags.CONSTANT_INTEGER:
                return new ConstantIntegerInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_FLOAT:
                return new ConstantFloatInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_LONG:
                return new ConstantLongInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_DOUBLE:
                return new ConstantDoubleInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_UTF_8:
                return new ConstantUtf8Info(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_STRING:
                return new ConstantStringInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_CLASS:
                return new ConstantClassInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_FIELD_REF:
                return new ConstantFieldrefInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_METHOD_REF:
                return new ConstantMethodrefInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_INTERFACE_METHOD_REF:
                return new ConstantInterfaceMethodrefInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_NAME_AND_TYPE:
                return new ConstantNameAndTypeInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_INVOKE_DYNAMIC_INFO:
                return new ConstantInvokeDynamicInfo(constantPoolInfo);
            case ConstantPoolTags.CONSTANT_METHOD_HANDLE:
                return new ConstantMethodHandlerInfo();
            case ConstantPoolTags.CONSTANT_METHOD_TYPE:
                return new ConstantMethodTypeInfo();
            default:
                throw new RuntimeException("Unknown constant pool tag: " + tag);
        }
    }
}