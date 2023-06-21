package com.lilhui.jvm.instructions.constants;

import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.instructions.base.U1IndexInstruction;
import com.lilhui.jvm.instructions.base.U2IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.Constant;
import com.lilhui.jvm.rtda.heap.StringPool;
import com.lilhui.jvm.rtda.heap.constant.*;
import com.lilhui.jvm.rtda.heap.Object;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 17:08
 */
// All instructions push values onto the operand stack
public class Constants {

    // **********************************************************
    // nop
    public static class NOP extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            // Do nothing
        }
    }

    // **********************************************************
    // Push null onto the operand stack
    public static class ACONST_NULL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushRef(null);
        }
    }

    // **********************************************************
    // Push double 0.0 onto the operand stack
    public static class DCONST_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushDouble(0.0);
        }
    }

    // **********************************************************
    // Push double 1.0 onto the operand stack
    public static class DCONST_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushDouble(1.0);
        }
    }

    // **********************************************************
    // Push float 0.0 onto the operand stack
    public static class FCONST_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushFloat(0.0f);
        }
    }

    // **********************************************************
    // Push float 1.0 onto the operand stack
    public static class FCONST_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushFloat(1.0f);
        }
    }

    // **********************************************************
    // Push float 2.0 onto the operand stack
    public static class FCONST_2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushFloat(2.0f);
        }
    }

    // **********************************************************
    // Push int -1 onto the operand stack
    public static class ICONST_M1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(-1);
        }
    }

    // **********************************************************
    // Push int 0 onto the operand stack
    public static class ICONST_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(0);
        }
    }

    // **********************************************************
    // Push int 1 onto the operand stack
    public static class ICONST_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(1);
        }
    }

    // **********************************************************
    // Push int 2 onto the operand stack
    public static class ICONST_2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(2);
        }
    }

    // **********************************************************
    // Push int 3 onto the operand stack
    public static class ICONST_3 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(3);
        }
    }

    // **********************************************************
// Push int 4 onto the operand stack
    public static class ICONST_4 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(4);
        }
    }

    // **********************************************************
// Push int 5 onto the operand stack
    public static class ICONST_5 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(5);
        }
    }

    // **********************************************************
// Push long 0 onto the operand stack
    public static class LCONST_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushLong(0);
        }
    }

    // **********************************************************
// Push long 1 onto the operand stack
    public static class LCONST_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushLong(1);
        }
    }

    // **********************************************************
// Read a byte value from the instruction code, sign extend it to int, and push it onto the operand stack
    public static class BIPUSH extends NoOperandsInstruction {

        private short value;

        @Override
        public void readOperands(CodeReader reader) {
            value = reader.readI1();
        }

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(value);
        }
    }

    // **********************************************************
// Read a short value from the instruction code, sign extend it to int, and push it onto the operand stack
    public static class SIPUSH extends NoOperandsInstruction {

        private short value;

        @Override
        public void readOperands(CodeReader reader) {
            value = reader.readI2();
        }

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.pushInt(value);
        }
    }

    // **********************************************************
    // 从运行时常量池中加载常量推入操作数栈中
    public static class LDC extends U1IndexInstruction {
        @Override
        public void execute(Frame frame) {
            ldc(frame, getIndex());
        }
    }

    public static class LDC_W extends U2IndexInstruction {
        @Override
        public void execute(Frame frame) {
            ldc(frame, getIndex());
        }
    }

    public static class LDC2_W extends U2IndexInstruction {
        @Override
        public void execute(Frame frame) {
            ldc2(frame, getIndex());
        }
    }

    private static void ldc(Frame frame, int index) {
        Constant constant = frame.getMethod().getClazz().getConstantPool().getConstant(index);
        OPStack stack = frame.getOpStack();
        Clazz clazz = frame.getMethod().getClazz();
        if (constant instanceof ConstantInteger) {
            stack.pushInt(((ConstantInteger) constant).getValue());
        }
        if (constant instanceof ConstantFloat) {
            stack.pushFloat(((ConstantFloat) constant).getValue());
        }
        if (constant instanceof ConstantString) {
            Object internedStr = StringPool.stringToJvmString(clazz.getLoader(), constant.getValue());
            stack.pushRef(internedStr);
        }
    }

    private static void ldc2(Frame frame, int index) {
        Constant constant = frame.getMethod().getClazz().getConstantPool().getConstant(index);
        OPStack stack = frame.getOpStack();
        if (constant instanceof ConstantLong) {
            stack.pushLong(((ConstantLong) constant).getValue());
        }
        if (constant instanceof ConstantDouble) {
            stack.pushDouble(((ConstantDouble) constant).getValue());
        }
    }
}