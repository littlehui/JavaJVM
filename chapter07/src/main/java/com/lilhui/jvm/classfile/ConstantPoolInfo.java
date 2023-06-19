package com.lilhui.jvm.classfile;

import com.lilhui.jvm.classfile.constant.*;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/26 13:48
 */
public class ConstantPoolInfo {

    private ConstantInfo[] constantPool;

    public ConstantPoolInfo(BytecodeReader reader) {
        int constantPoolCount = reader.readU2();
        constantPool = new ConstantInfo[constantPoolCount];
        for (int i = 1; i < constantPoolCount; i++) {
            ConstantInfo constantInfo = ConstantInfos.newConstantInfo(reader, this);
            constantInfo.readInfo(reader);
            constantPool[i] = (constantInfo);

            // If it's ConstantLong or ConstantDouble, increment i
            if (constantInfo instanceof ConstantLongInfo || constantInfo instanceof ConstantDoubleInfo) {
                i++;
            }
        }
    }

    public String getUtf8(int index) {
        ConstantUtf8Info utf8 = getConstantInfo(index, ConstantUtf8Info.class);
        if (utf8 == null) {
            throw new RuntimeException("Invalid constant pool index: " + index + " getUtf8");
        }
        return utf8.getValue();
    }

    public ConstantInfo getConstantInfo(int index) {
        return constantPool[index];
    }

    public <T extends ConstantInfo> T getConstantInfo(int index, Class<T> type) {
        ConstantInfo info = getConstantInfo(index);
        if (info != null) {
            if (type.isInstance(info)) {
                return type.cast(info);
            }
        }
        return null;
    }

    public String getClassName(int classIndex) {
        ConstantClassInfo constantClassInfo = (ConstantClassInfo) constantPool[classIndex];
        String className = getUtf8(constantClassInfo.getNameIndex());
        return className;
    }

    public String getNameAndType(int nameAndTypeIndex) {
        ConstantNameAndTypeInfo constantNameAndTypeInfo = (ConstantNameAndTypeInfo) constantPool[nameAndTypeIndex];
        String name = getUtf8(constantNameAndTypeInfo.getNameIndex());
        String type = getUtf8(constantNameAndTypeInfo.getDescriptorIndex());
        return name + type;
    }

    public int getSize() {
        return constantPool.length;
    }
}


