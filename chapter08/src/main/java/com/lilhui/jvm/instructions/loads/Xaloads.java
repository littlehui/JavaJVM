package com.lilhui.jvm.instructions.loads;

import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.ArrayObject;
import com.lilhui.jvm.rtda.heap.Object;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 16:34
 */
public class Xaloads {

    private static void checkNotNull(Object ref) {
        if (ref == null) {
            throw new NullPointerException();
        }
    }

    private static void checkIndex(int arrayLength, int index) {
        if (index < 0 || index >= arrayLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static class AALOAD extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack opStack = frame.getOpStack();
            int index = opStack.popInt();
            ArrayObject arrayRef = (ArrayObject) opStack.popRef();
            checkNotNull(arrayRef);
            Object[] objects = arrayRef.refs();
            checkIndex(objects.length, index);
            opStack.pushRef(objects[index]);
        }
    }

    public static class BALOAD extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack opStack = frame.getOpStack();
            int index = opStack.popInt();
            ArrayObject arrayRef = (ArrayObject) opStack.popRef();
            checkNotNull(arrayRef);
            byte[] objects = arrayRef.bytes();
            checkIndex(objects.length, index);
            opStack.pushInt(objects[index]);
        }
    }

    public static class CALOAD extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack opStack = frame.getOpStack();
            int index = opStack.popInt();
            ArrayObject arrayRef = (ArrayObject) opStack.popRef();
            checkNotNull(arrayRef);
            char[] objects = arrayRef.chars();
            checkIndex(objects.length, index);
            opStack.pushInt(objects[index]);
        }
    }

    public static class DALOAD extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack opStack = frame.getOpStack();
            int index = opStack.popInt();
            ArrayObject arrayRef = (ArrayObject) opStack.popRef();
            checkNotNull(arrayRef);
            double[] objects = arrayRef.doubles();
            checkIndex(objects.length, index);
            opStack.pushDouble(objects[index]);
        }
    }

    public static class FALOAD extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack opStack = frame.getOpStack();
            int index = opStack.popInt();
            ArrayObject arrayRef = (ArrayObject) opStack.popRef();
            checkNotNull(arrayRef);
            float[] objects = arrayRef.floats();
            checkIndex(objects.length, index);
            opStack.pushFloat(objects[index]);
        }
    }

    public static class IALOAD extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack opStack = frame.getOpStack();
            int index = opStack.popInt();
            ArrayObject arrayRef = (ArrayObject) opStack.popRef();
            checkNotNull(arrayRef);
            int[] objects = arrayRef.ints();
            checkIndex(objects.length, index);
            opStack.pushInt(objects[index]);
        }
    }

    public static class LALOAD extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack opStack = frame.getOpStack();
            int index = opStack.popInt();
            ArrayObject arrayRef = (ArrayObject) opStack.popRef();
            checkNotNull(arrayRef);
            long[] objects = arrayRef.longs();
            checkIndex(objects.length, index);
            opStack.pushLong(objects[index]);
        }
    }

    public static class SALOAD extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack opStack = frame.getOpStack();
            int index = opStack.popInt();
            ArrayObject arrayRef = (ArrayObject) opStack.popRef();
            checkNotNull(arrayRef);
            short[] objects = arrayRef.shorts();
            checkIndex(objects.length, index);
            opStack.pushInt(objects[index]);
        }
    }
}
