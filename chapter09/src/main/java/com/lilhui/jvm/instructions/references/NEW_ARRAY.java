package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.classfile.ClazzLoader;
import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.Instruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.Object;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 14:09
 */
public class NEW_ARRAY implements Instruction {

    @Getter
    @Setter
    private ArrayType arrayType;

    @Override
    public void readOperands(CodeReader reader) {
        int typeCode = reader.readU1();
        arrayType = ArrayType.getByCode(typeCode);
    }

    @Override
    public void execute(Frame frame) {
        OPStack stack = frame.getOpStack();
        int length = stack.popInt();
        if (length < 0) {
            throw new NegativeArraySizeException();
        }
        ClazzLoader loader = frame.getMethod().getClazz().getLoader();
        Clazz arrayClass = getBasicArrayClass(loader, arrayType);
        Object array = arrayClass.newArray(length);
        stack.pushRef(array);
    }

    private Clazz getBasicArrayClass(ClazzLoader loader, ArrayType arrayType) {
        return loader.loadClass(arrayType.getTypeName());
    }

    enum ArrayType {
        AT_BOOLEAN(4, "[Z"),
        AT_CHAR(5, "[C"),
        AT_FLOAT(6, "[F"),
        AT_DOUBLE(7, "[D"),
        AT_BYTE(8, "[B"),
        AT_SHORT(9, "[S"),
        AT_INT(10, "[I"),
        AT_LONG(11, "[L");

        @Getter
        @Setter
        int typeCode;

        @Getter
        @Setter
        String typeName;

        ArrayType(int typeCode, String typeName) {
            this.typeCode = typeCode;
            this.typeName = typeName;
        }

        public static ArrayType getByCode(int typeCode) {
            for (ArrayType type : ArrayType.values()) {
                if (type.typeCode == typeCode) {
                    return type;
                }
            }
            return null;
        }
    }
}

