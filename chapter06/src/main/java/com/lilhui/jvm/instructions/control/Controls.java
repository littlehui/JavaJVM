package com.lilhui.jvm.instructions.control;

import com.lilhui.jvm.instructions.base.BranchInstruction;
import com.lilhui.jvm.instructions.base.BranchLogic;
import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;

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
}
