package com.lilhui.jvm.instructions.references;

import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.Instruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.*;
import com.lilhui.jvm.rtda.heap.Object;
import com.lilhui.jvm.rtda.heap.constant.ClazzRef;
import lombok.Getter;
import lombok.Setter;


/**
 * TODO
 * @author littlehui
 * @date 2023/06/20 17:51
 * @version 1.0
 */
public class MULTI_ANEW_ARRAY implements Instruction {

    @Getter
    @Setter
    private int index;

    @Getter
    @Setter
    private int dimensions;

    @Override
    public void readOperands(CodeReader reader) {
        this.index = reader.readU2();
        this.dimensions = reader.readU1();
    }

    @Override
    public void execute(Frame frame) {
        ConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClazzRef clazzRef = (ClazzRef) constantPool.getConstant(index);
        ArrayClazz arrayClazz = (ArrayClazz) clazzRef.resolvedClass();
        OPStack opStack = frame.getOpStack();
        int[] counts = popAndCheckCounts(opStack, dimensions);
        java.lang.Object arrRef = newMultiDimensionalArray(counts, arrayClazz);
        opStack.pushRef((ArrayObject)arrRef);
    }

    private java.lang.Object newMultiDimensionalArray(int[] counts, Clazz clazz) {
        int count = counts[0];
        ArrayObject arrayRef = (ArrayObject) clazz.newArray(count);
        if (counts.length > 1) {
            java.lang.Object refs = arrayRef.refObj();
            for (int i = 0; i < lengthObject(refs); i++) {
                int[] nextCounts = new int[counts.length - 1];
                for (int j = 1; j < counts.length; j++) {
                    nextCounts[j-1] = counts[j];
                }
                setObject(refs, i, newMultiDimensionalArray(nextCounts, clazz.getComponentClazz()));
            }
        }
        return arrayRef;
    }

    private void setObject(java.lang.Object object, int index, java.lang.Object objValue) {
        if (object instanceof byte[]) {
            ((byte[]) object)[index] = (byte)objValue;
        } else if (object instanceof short[]) {
            ((short[]) object)[index] = (short)objValue;
        } else if (object instanceof int[]) {
            ((int[]) object)[index] = (int)objValue;
        } else if (object instanceof long[]) {
            ((long[]) object)[index] = (long)objValue;
        } else if (object instanceof char[]) {
            ((char[]) object)[index] = (char)objValue;
        } else if (object instanceof float[]) {
            ((float[]) object)[index] = (float)objValue;
        } else if (object instanceof double[]) {
            ((double[]) object)[index] = (double)objValue;
        } else if (object instanceof Object[]) {
            ((Object[]) object)[index] = (Object)objValue;
        } else {
            throw new RuntimeException("Not array!");
        }
    }

    private int lengthObject(java.lang.Object object) {
        if (object instanceof byte[]) {
            return ((byte[]) object).length;
        } else if (object instanceof short[]) {
            return ((short[]) object).length;
        } else if (object instanceof int[]) {
            return ((int[]) object).length;
        } else if (object instanceof long[]) {
            return ((long[]) object).length;
        } else if (object instanceof char[]) {
            return ((char[]) object).length;
        } else if (object instanceof float[]) {
            return ((float[]) object).length;
        } else if (object instanceof double[]) {
            return ((double[]) object).length;
        } else if (object instanceof Object[]) {
            return ((Object[]) object).length;
        } else {
            throw new RuntimeException("Not array!");
        }
    }
    
    private int[] popAndCheckCounts(OPStack opStack, int dimensions) {
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = opStack.popInt();
            if (counts[i] < 0) {
                throw new NegativeArraySizeException();
            }
        }
        return counts;
    }


}
