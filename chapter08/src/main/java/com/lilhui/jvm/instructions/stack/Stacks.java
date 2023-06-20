package com.lilhui.jvm.instructions.stack;

import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.Slot;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/10 16:22
 */
public class Stacks {

    // 对操作数栈做相关出入栈操作
// *****************************************************************
// 操作数栈顶的一个Slot出栈
    public static class POP extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.popSlot();
        }
    }

    // *****************************************************************
// 操作数栈顶的两个Slot出栈
    public static class POP2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            stack.popSlot();
            stack.popSlot();
        }
    }

    // *****************************************************************
// 交换栈顶的两个变量
    public static class SWAP extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Slot slot1 = stack.popSlot();
            Slot slot2 = stack.popSlot();
            stack.pushSlot(slot1);
            stack.pushSlot(slot2);
        }
    }

    // *****************************************************************
/*
bottom -> top
[...][c][b][a]
             \_
               |
               V
[...][c][b][a][a]
*/
    public static class DUP extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Slot slot = stack.popSlot();
            //判断是否是double或者long
            stack.pushSlot(slot);
            stack.pushSlot(slot.dupSlot());
        }
    }

    // *****************************************************************
/*
bottom -> top
[...][c][b][a]
          __/
         |
         V
[...][c][a][b][a]
*/
    public static class DUP_X1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Slot slot1 = stack.popSlot();
            Slot slot2 = stack.popSlot();
            stack.pushSlot(slot1);
            stack.pushSlot(slot2);
            stack.pushSlot(slot1.dupSlot());
        }
    }

    // *****************************************************************
/*
bottom -> top
[...][c][b][a]
       _____/
      |
      V
[...][a][c][b][a]
*/
    public static class DUP_X2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Slot slot1 = stack.popSlot();
            Slot slot2 = stack.popSlot();
            Slot slot3 = stack.popSlot();
            stack.pushSlot(slot1);
            stack.pushSlot(slot3);
            stack.pushSlot(slot2);
            stack.pushSlot(slot1.dupSlot());
        }
    }

    // *****************************************************************
/*
bottom -> top
[...][c][b][a]____
          \____   |
               |  |
               V  V
[...][c][b][a][b][a]
*/
    public static class DUP2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Slot slot1 = stack.popSlot();
            Slot slot2 = stack.popSlot();
            stack.pushSlot(slot2);
            stack.pushSlot(slot1);
            stack.pushSlot(slot2.dupSlot());
            stack.pushSlot(slot1.dupSlot());
        }
    }

    // *****************************************************************
/*
bottom -> top
[...][c][b][a]
       _/ __/
      |  |
      V  V
[...][b][a][c][b][a]
*/
    public static class DUP2_X1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Slot slot1 = stack.popSlot();
            Slot slot2 = stack.popSlot();
            Slot slot3 = stack.popSlot();
            stack.pushSlot(slot2);
            stack.pushSlot(slot1);
            stack.pushSlot(slot3);
            stack.pushSlot(slot2.dupSlot());
            stack.pushSlot(slot1.dupSlot());
        }
    }

    // *****************************************************************
/*
bottom -> top
[...][d][c][b][a]
       ____/ __/
      |   __/
      V  V
[...][b][a][d][c][b][a]
*/
    public static class DUP2_X2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Slot slot1 = stack.popSlot();
            Slot slot2 = stack.popSlot();
            Slot slot3 = stack.popSlot();
            Slot slot4 = stack.popSlot();
            stack.pushSlot(slot2);
            stack.pushSlot(slot1);
            stack.pushSlot(slot4);
            stack.pushSlot(slot3);
            stack.pushSlot(slot2.dupSlot());
            stack.pushSlot(slot1.dupSlot());
        }
    }

}
