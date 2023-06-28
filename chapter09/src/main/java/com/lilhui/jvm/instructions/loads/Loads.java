package com.lilhui.jvm.instructions.loads;

import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.instructions.base.U1IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.LocalVars;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.Object;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/10 16:52
 */
public class Loads {

    static void iload(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        int val = localVars.getInt(index);
        stack.pushInt(val);
    }

    static void lload(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        long val = localVars.getLong(index);
        stack.pushLong(val);
    }

    static void fload(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        float val = localVars.getFloat(index);
        stack.pushFloat(val);
    }

    static void dload(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        double val = localVars.getDouble(index);
        stack.pushDouble(val);
    }

    static void aload(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        Object val = localVars.getRef(index);
        stack.pushRef(val);
    }


    // *****************************************************************
    public static class ILOAD extends U1IndexInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.iload(frame, getIndex());
        }
    }

    // *****************************************************************
    public static class ILOAD_0 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.iload(frame, 0);
        }
    }

    // *****************************************************************
    public static class ILOAD_1 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.iload(frame, 1);
        }
    }

    // *****************************************************************
    public static class ILOAD_2 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.iload(frame, 2);
        }
    }

    // *****************************************************************
    public static class ILOAD_3 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.iload(frame, 3);
        }
    }

    // *****************************************************************
    public static class LLOAD extends U1IndexInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.lload(frame, getIndex());
        }
    }

    // *****************************************************************
    public static class LLOAD_0 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.lload(frame, 0);
        }
    }

    // *****************************************************************
    public static class LLOAD_1 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.lload(frame, 1);
        }
    }

    // *****************************************************************
    public static class LLOAD_2 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.lload(frame, 2);
        }
    }

    // *****************************************************************
    public static class LLOAD_3 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.lload(frame, 3);
        }
    }

    // *****************************************************************
    public static class FLOAD extends U1IndexInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.fload(frame, getIndex());
        }
    }

    // *****************************************************************
    public static class FLOAD_0 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.fload(frame, 0);
        }
    }

    // *****************************************************************
    public static class FLOAD_1 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.fload(frame, 1);
        }
    }

    // *****************************************************************
    public static class FLOAD_2 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.fload(frame, 2);
        }
    }

    // *****************************************************************
    public static class FLOAD_3 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.fload(frame, 3);
        }
    }

    // *****************************************************************
    public static class DLOAD extends U1IndexInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.dload(frame, getIndex());
        }
    }

    // *****************************************************************
    public static class DLOAD_0 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.dload(frame, 0);
        }
    }

    // *****************************************************************
    public static class DLOAD_1 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.dload(frame, 1);
        }
    }

    // *****************************************************************
    public static class DLOAD_2 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.dload(frame, 2);
        }
    }

    // *****************************************************************
    public static class DLOAD_3 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.dload(frame, 3);
        }
    }

    // *****************************************************************
    public static class ALOAD extends U1IndexInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.aload(frame, getIndex());
        }
    }

    // *****************************************************************
    public static class ALOAD_0 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.aload(frame, 0);
        }
    }

    // *****************************************************************
    public static class ALOAD_1 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.aload(frame, 1);
        }
    }

    // *****************************************************************
    public static class ALOAD_2 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.aload(frame, 2);
        }
    }

    // *****************************************************************
    public static class ALOAD_3 extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            Loads.aload(frame, 3);
        }
    }
}
