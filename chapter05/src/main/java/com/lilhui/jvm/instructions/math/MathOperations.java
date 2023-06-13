package com.lilhui.jvm.instructions.math;

import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.Instruction;
import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 18:01
 */
import java.lang.Math;

public class MathOperations {
    // int型取余数
    // 栈顶元素为除数
    // 次栈顶元素为被除数
    // a / b   a为被除数，b为除数
    public static class IREM extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            if (v2 == 0) {
                throw new ArithmeticException("/ by zero");
            }
            int result = v1 % v2;
            stack.pushInt(result);
        }
    }

    // long型取余数
    // 栈顶元素为除数
    // 次栈顶元素为被除数
    // a / b   a为被除数，b为除数
    public static class LREM extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            if (v2 == 0) {
                throw new ArithmeticException("/ by zero");
            }
            long result = v1 % v2;
            stack.pushLong(result);
        }
    }

    // float型取余数
    // 栈顶元素为除数
    // 次栈顶元素为被除数
    // a / b   a为被除数，b为除数
    public static class FREM extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float v2 = stack.popFloat();
            float v1 = stack.popFloat();
            float result = (float) Math.IEEEremainder(v1, v2);
            stack.pushFloat(result);
        }
    }

    // double型取余数
    // 栈顶元素为除数
    // 次栈顶元素为被除数
    // a / b   a为被除数，b为除数
    public static class DREM extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double v2 = stack.popDouble();
            double v1 = stack.popDouble();
            double result = Math.IEEEremainder(v1, v2);
            stack.pushDouble(result);
        }
    }

    // int型左移运算
    // 栈顶第一个数为左移的位数
    // 栈顶第二个数为被左移的数
    public static class ISHL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 << v2;
            stack.pushInt(result);
        }
    }

    // int型算术右移运算
    // 栈顶第一个数为右移的位数
    // 栈顶第二个数为被右移的数
    public static class ISHR extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 >> v2;
            stack.pushInt(result);
        }
    }

    // int型逻辑右移运算
    // 栈顶第一个数为右移的位数
    // 栈顶第二个数为被右移的数
    public static class IUSHR extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 >>> v2;
            stack.pushInt(result);
        }
    }

    // long型左移运算
    // 栈顶第一个数为左移的位数
    // 栈顶第二个数为被左移的数
    public static class LSHL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            long v1 = stack.popLong();
            long result = v1 << v2;
            stack.pushLong(result);
        }
    }

    // long型算术右移运算
    // 栈顶第一个数为右移的位数
    // 栈顶第二个数为被右移的数
    public static class LSHR extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            long v1 = stack.popLong();
            long result = v1 >> v2;
            stack.pushLong(result);
        }
    }

    // long型逻辑右移运算
    // 栈顶第一个数为右移的位数
    // 栈顶第二个数为被右移的数
    public static class LUSHR extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            long v1 = stack.popLong();
            long result = v1 >>> v2;
            stack.pushLong(result);
        }
    }

    // int型按位与运算
    // 栈顶第一个数为第二个数与第一个数按位与的结果
    // 栈顶第二个数为第二个数
    public static class IAND extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 & v2;
            stack.pushInt(result);
        }
    }

    // long型按位与运算
    // 栈顶第一个数为第二个数与第一个数按位与的结果
    // 栈顶第二个数为第二个数
    public static class LAND extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            long result = v1 & v2;
            stack.pushLong(result);
        }
    }

    // int型按位或运算
    // 栈顶第一个数为第二个数与第一个数按位或的结果
    // 栈顶第二个数为第二个数
    public static class IOR extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 | v2;
            stack.pushInt(result);
        }
    }

    // long型按位或运算
    // 栈顶第一个数为第二个数与第一个数按位或的结果
    // 栈顶第二个数为第二个数
    public static class LOR extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            long result = v1 | v2;
            stack.pushLong(result);
        }
    }

    // int型按位异或运算
    // 栈顶第一个数为第二个数与第一个数按位异或的结果
    // 栈顶第二个数为第二个数
    public static class IXOR extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 ^ v2;
            stack.pushInt(result);
        }
    }

    // long型按位异或运算
    // 栈顶第一个数为第二个数与第一个数按位异或的结果
    // 栈顶第二个数为第二个数
    public static class LXOR extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            long result = v1 ^ v2;
            stack.pushLong(result);
        }
    }

    // 取栈顶元素做运算，结果放回栈顶(无操作数)
    // IINC指令是例外 将本地变量表索引位置的int型变量加上指定值后存回该索引位置
    // 指令码后一个字节是索引，再后一个字节是要加的值
    // *****************************************************************
    @Data
    public static class IINC extends NoOperandsInstruction {

        private int index;

        private int constValue;

        @Override
        public void readOperands(CodeReader reader) {
            // No operands to read
            index = reader.readU1();
            constValue = reader.readI1();
        }

        @Override
        public void execute(Frame frame) {
            int value = frame.getLocalVars().getInt(index);
            value += constValue;
            frame.getLocalVars().setInt(index, value);
        }
    }

    // *****************************************************************
    public static class IADD extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 + v2;
            stack.pushInt(result);
        }
    }

    // *****************************************************************
    public static class LADD extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            long result = v1 + v2;
            stack.pushLong(result);
        }
    }

    // *****************************************************************
    public static class FADD extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float v2 = stack.popFloat();
            float v1 = stack.popFloat();
            float result = v1 + v2;
            stack.pushFloat(result);
        }
    }

    // *****************************************************************
    public static class DADD extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double v2 = stack.popDouble();
            double v1 = stack.popDouble();
            double result = v1 + v2;
            stack.pushDouble(result);
        }
    }

    // *****************************************************************
    public static class ISUB extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 - v2;
            stack.pushInt(result);
        }
    }

    // *****************************************************************
    public static class LSUB extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            long result = v1 - v2;
            stack.pushLong(result);
        }
    }

    // *****************************************************************
    public static class FSUB extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float v2 = stack.popFloat();
            float v1 = stack.popFloat();
            float result = v1 - v2;
            stack.pushFloat(result);
        }
    }

    // *****************************************************************
    public static class DSUB extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double v2 = stack.popDouble();
            double v1 = stack.popDouble();
            double result = v1 - v2;
            stack.pushDouble(result);
        }
    }

    // *****************************************************************
    public static class IMUL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            int result = v1 * v2;
            stack.pushInt(result);
        }
    }

    // *****************************************************************
    public static class LMUL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            long result = v1 * v2;
            stack.pushLong(result);
        }
    }

    // *****************************************************************
    public static class FMUL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float v2 = stack.popFloat();
            float v1 = stack.popFloat();
            float result = v1 * v2;
            stack.pushFloat(result);
        }
    }

    // *****************************************************************
    public static class DMUL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double v2 = stack.popDouble();
            double v1 = stack.popDouble();
            double result = v1 * v2;
            stack.pushDouble(result);
        }
    }

    // *****************************************************************
    public static class IDIV extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            if (v2 == 0) {
                throw new ArithmeticException("/ by zero");
            }
            int result = v1 / v2;
            stack.pushInt(result);
        }
    }

    // *****************************************************************
    public static class LDIV extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            if (v2 == 0) {
                throw new ArithmeticException("/ by zero");
            }
            long result = v1 / v2;
            stack.pushLong(result);
        }
    }

    // *****************************************************************
    public static class FDIV extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float v2 = stack.popFloat();
            float v1 = stack.popFloat();
            float result = v1 / v2;
            stack.pushFloat(result);
        }
    }

    // *****************************************************************
    public static class DDIV extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double v2 = stack.popDouble();
            double v1 = stack.popDouble();
            double result = v1 / v2;
            stack.pushDouble(result);
        }
    }

    // *****************************************************************
    public static class INEG extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int val = stack.popInt();
            stack.pushInt(-val);
        }
    }

    // *****************************************************************
    public static class LNEG extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long val = stack.popLong();
            stack.pushLong(-val);
        }
    }

    // *****************************************************************
    public static class FNEG extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            float val = stack.popFloat();
            stack.pushFloat(-val);
        }
    }

    // *****************************************************************
    public static class DNEG extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            double val = stack.popDouble();
            stack.pushDouble(-val);
        }
    }

}
