package com.lilhui.jvm.instructions.control;

import com.lilhui.jvm.instructions.base.BranchInstruction;
import com.lilhui.jvm.instructions.base.BranchLogic;
import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.Thread;
import com.lilhui.jvm.rtda.heap.Object;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 17:20
 */
public class Controls {

    public static class GOTO extends BranchInstruction {

        @Override
        public void execute(Frame frame) {
            BranchLogic.branch(frame, offset);
        }
    }

    public static class TABLE_SWITCH extends BranchInstruction {
        private int defaultOffset;
        private int low;
        private int high;
        private int[] jumpOffsets;

        @Override
        public void readOperands(CodeReader reader) {
            reader.skipPadding();
            defaultOffset = reader.readI4();
            low = reader.readI4();
            high = reader.readI4();
            int jumpOffsetCount = high - low + 1;
            jumpOffsets = new int[jumpOffsetCount];
            for (int i = 0; i < jumpOffsetCount; i++) {
                jumpOffsets[i] = reader.readI4();
            }
        }

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int key = stack.popInt();
            int offset;
            if (key >= low && key <= high) {
                offset = jumpOffsets[key - low];
            } else {
                offset = defaultOffset;
            }
            BranchLogic.branch(frame, offset);
        }
    }

    public static class LOOKUP_SWITCH extends BranchInstruction {
        private int defaultOffset;
        private int npairs;
        private int[] matchOffsets;

        @Override
        public void readOperands(CodeReader reader) {
            reader.skipPadding();
            defaultOffset = reader.readI4();
            npairs = reader.readI4();
            matchOffsets = new int[npairs*2];
            for (int i = 0; i < npairs*2; i++) {
                matchOffsets[i] = reader.readI4();
            }
        }

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int key = stack.popInt();
            int offset = defaultOffset;
            for (int i = 0; i < npairs * 2; i += 2) {
                if (matchOffsets[i] == key) {
                    offset = matchOffsets[i + 1];
                    break;
                }
            }
            BranchLogic.branch(frame, offset);
        }
    }

    public static class RETURN extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            frame.getThread().popFrame();
        }
    }

    public static class ARETURN extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            Thread thread = frame.getThread();
            Frame currentFrame = thread.popFrame();
            Frame invokerFrame = thread.topFrame();
            Object retVal = currentFrame.getOpStack().popRef();
            invokerFrame.getOpStack().pushRef(retVal);
        }
    }

    public static class DRETURN extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            Thread thread = frame.getThread();
            Frame currentFrame = thread.popFrame();
            Frame invokerFrame = thread.topFrame();
            double retVal = currentFrame.getOpStack().popDouble();
            invokerFrame.getOpStack().pushDouble(retVal);
        }
    }

    public static class FRETURN extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            Thread thread = frame.getThread();
            Frame currentFrame = thread.popFrame();
            Frame invokerFrame = thread.topFrame();
            float retVal = currentFrame.getOpStack().popFloat();
            invokerFrame.getOpStack().pushFloat(retVal);
        }
    }

    public static class IRETURN extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            Thread thread = frame.getThread();
            Frame currentFrame = thread.popFrame();
            Frame invokerFrame = thread.topFrame();
            int retVal = currentFrame.getOpStack().popInt();
            invokerFrame.getOpStack().pushInt(retVal);
        }
    }

    public static class LRETURN extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            Thread thread = frame.getThread();
            Frame currentFrame = thread.popFrame();
            Frame invokerFrame = thread.topFrame();
            long retVal = currentFrame.getOpStack().popLong();
            invokerFrame.getOpStack().pushLong(retVal);
        }
    }
}
