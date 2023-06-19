package com.lilhui.jvm.instructions.conversions;

import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 17:33
 */
public class Conversions {
    
    //弹出栈顶元素，将其转化为其他类型后入栈(不需操作数)
    //栈顶int元素弹出，转成byte型后入栈
    public static class I2B extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            byte b = (byte) i;
            stack.pushInt(b);
        }
    }

    
    //栈顶int元素弹出，转成char型后入栈
    public static class I2C extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            char c = (char) i;
            stack.pushInt(c);
        }
    }

    
    //栈顶int元素弹出，转成short型后入栈
    public static class I2S extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            short s = (short) i;
            stack.pushInt(s);
        }
    }



        //栈顶int元素弹出，转成long型后入栈
    public static class I2L extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            long l = i;
            stack.pushLong(l);
        }
    }

    
    public static class I2F extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            float f = i;
            stack.pushFloat(f);
        }
    }

    
    public static class I2D extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            double d = i;
            stack.pushDouble(d);
        }
    }

    
    //栈顶long元素弹出，转成int型后入栈
    public static class L2I extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long l = stack.popLong();
            int i = (int) l;
            stack.pushInt(i);
        }
    }

    
    public static class L2F extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long l = stack.popLong();
            float f = l;
            stack.pushFloat(f);
        }
    }

    
    public static class L2D extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long l = stack.popLong();
            double d = l;
            stack.pushDouble(d);
        }
    }

    
    //栈顶float元素弹出，转成int型后入栈
    public static class F2I extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float f = stack.popFloat();
            int i = (int) f;
            stack.pushInt(i);
        }
    }

    public static class F2L extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float f = stack.popFloat();
            long l = (long) f;
            stack.pushLong(l);
        }
    }

    
    public static class F2D extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float f = stack.popFloat();
            double d = f;
            stack.pushDouble(d);
        }
    }

    
    //栈顶double元素弹出，转成int型后入栈
    public static class D2I extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double d = stack.popDouble();
            int i = (int) d;
            stack.pushInt(i);
        }
    }

    
    public static class D2L extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double d = stack.popDouble();
            long l = (long) d;
            stack.pushLong(l);
        }
    }

    
    public static class D2F extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double d = stack.popDouble();
            float f = (float) d;
            stack.pushFloat(f);
        }
    }

}
