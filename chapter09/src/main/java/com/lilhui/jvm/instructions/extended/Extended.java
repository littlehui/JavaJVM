package com.lilhui.jvm.instructions.extended;

import com.lilhui.jvm.instructions.base.BranchInstruction;
import com.lilhui.jvm.instructions.base.BranchLogic;
import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.Instruction;
import com.lilhui.jvm.instructions.loads.Loads.*;
import com.lilhui.jvm.instructions.math.MathOperations;
import com.lilhui.jvm.instructions.stores.Stores.*;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 17:37
 */
public class Extended {

    // *****************************************************************
// 栈顶弹出引用，若引用为null，跳转
    public static class IFNULL extends BranchInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Object ref = stack.popRef();
            if (ref == null) {
                BranchLogic.branch(frame, super.getOffset());
            }
        }
    }

    // *****************************************************************
    public static class IFNONNULL extends BranchInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Object ref = stack.popRef();
            if (ref != null) {
                BranchLogic.branch(frame, super.getOffset());
            }
        }
    }

    // *****************************************************************
// 无条件跳转，offset为四字节
    public static class GOTO_W extends BranchInstruction {

        @Override
        public void execute(Frame frame) {
            BranchLogic.branch(frame, super.getOffset());
        }
    }

    // *****************************************************************
    public static class WIDE extends BranchInstruction {

        private Instruction wideInst;

        @Override
        public void readOperands(CodeReader reader) {
            int opcode = reader.readU1();
            switch (opcode) {
                case 0x15:
                    ILOAD inst = new ILOAD();
                    inst.setIndex(reader.readU2());
                    wideInst = inst;
                    break;
                case 0x16:
                    LLOAD inst2 = new LLOAD();
                    inst2.setIndex(reader.readU2());
                    wideInst = inst2;
                    break;
                case 0x17:
                    FLOAD inst3 = new FLOAD();
                    inst3.setIndex(reader.readU2());
                    wideInst = inst3;
                    break;
                case 0x18:
                    DLOAD inst4 = new DLOAD();
                    inst4.setIndex(reader.readU2());
                    wideInst = inst4;
                    break;
                case 0x19:
                    ALOAD inst5 = new ALOAD();
                    inst5.setIndex(reader.readU2());
                    wideInst = inst5;
                    break;
                case 0x36:
                    ISTORE inst6 = new ISTORE();
                    inst6.setIndex(reader.readU2());
                    wideInst = inst6;
                    break;
                case 0x37:
                    LSTORE inst7 = new LSTORE();
                    inst7.setIndex(reader.readU2());
                    wideInst = inst7;
                    break;
                case 0x38:
                    FSTORE inst8 = new FSTORE();
                    inst8.setIndex(reader.readU2());
                    wideInst = inst8;
                    break;
                case 0x39:
                    DSTORE inst9 = new DSTORE();
                    inst9.setIndex(reader.readU2());
                    wideInst = inst9;
                    break;
                case 0x3a:
                    ASTORE inst10 = new ASTORE();
                    inst10.setIndex(reader.readU2());
                    wideInst = inst10;
                    break;
                case 0x84:
                    MathOperations.IINC inst11 = new MathOperations.IINC();
                    inst11.setIndex(reader.readU2());
                    inst11.setConstValue(reader.readI2());
                    wideInst = inst11;
                    break;
                case 0xa9: // ret
                    throw new UnsupportedOperationException("Unsupported opcode: 0xa9!");
            }
        }

        @Override
        public void execute(Frame frame) {
            wideInst.execute(frame);
        }
    }
}
