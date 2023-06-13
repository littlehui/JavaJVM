package com.lilhui.jvm.classfile;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/26 13:48
 */
public class ConstantPool {

    private ConstantInfo[] constantPool;

    public ConstantPool(BytecodeReader reader) {
        int constantPoolCount = reader.readU2();
        constantPool = new ConstantInfo[constantPoolCount];
        for (int i = 1; i < constantPoolCount; i++) {
            ConstantInfo constantInfo = ConstantInfos.newConstantInfo(reader, this);
            constantInfo.readInfo(reader);
            constantPool[i] = (constantInfo);

            // If it's ConstantLong or ConstantDouble, increment i
            if (constantInfo instanceof ConstantLong || constantInfo instanceof ConstantDouble) {
                i++;
            }
        }
    }

    public String getUtf8(int index) {
        ConstantUtf8 utf8 = getConstantInfo(index, ConstantUtf8.class);
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
        if (type.isInstance(info)) {
            return type.cast(info);
        }
        return null;
    }

    public String getClassName(int classIndex) {
        ConstantClass constantClass = (ConstantClass) constantPool[classIndex];
        String className = getUtf8(constantClass.getNameIndex());
        return className;
    }

    public String getNameAndType(int nameAndTypeIndex) {
        ConstantNameAndType constantNameAndType = (ConstantNameAndType) constantPool[nameAndTypeIndex];
        String name = getUtf8(constantNameAndType.getNameIndex());
        String type = getUtf8(constantNameAndType.getDescriptorIndex());
        return name + type;
    }

    public int getSize() {
        return constantPool.length;
    }
}


