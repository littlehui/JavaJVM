package com.lilhui.jvm.instructions.stores;

import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.instructions.base.U1IndexInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.LocalVars;
import com.lilhui.jvm.rtda.OPStack;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/10 16:26
 */
public class Stores {

    // 将操作数栈顶弹出的变量放到局部变量表的指定索引处
    // 索引由操作码自带或操作数给出。

    // istore_x操作通用函数
    private static void istore(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        localVars.setInt(index, stack.popInt());
    }

    // ISTORE
    public static class ISTORE extends U1IndexInstruction {
        @Override
        public void execute(Frame frame) {
            int index = getIndex();
            istore(frame, index);
        }
    }

    // ISTORE_0
    public static class ISTORE_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            istore(frame, 0);
        }
    }

    // ISTORE_1
    public static class ISTORE_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            istore(frame, 1);
        }
    }

    // ISTORE_2
    public static class ISTORE_2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            istore(frame, 2);
        }
    }

    // ISTORE_3
    public static class ISTORE_3 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            istore(frame, 3);
        }
    }

    // lstore_x操作通用函数
    private static void lstore(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        localVars.setLong(index, stack.popLong());
    }

    // LSTORE
    public static class LSTORE extends U1IndexInstruction {
        @Override
        public void execute(Frame frame) {
            int index = getIndex();
            lstore(frame, index);
        }
    }

    // LSTORE_0
    public static class LSTORE_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            lstore(frame, 0);
        }
    }

    // LSTORE_1
    public static class LSTORE_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            lstore(frame, 1);
        }
    }

    // LSTORE_2
    public static class LSTORE_2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            lstore(frame, 2);
        }
    }

    // LSTORE_3
    public static class LSTORE_3 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            lstore(frame, 3);
        }
    }

    // fstore_x操作通用函数
    private static void fstore(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        localVars.setFloat(index, stack.popFloat());
    }

    // FSTORE
    public static class FSTORE extends U1IndexInstruction {
        @Override
        public void execute(Frame frame) {
            int index = getIndex();
            fstore(frame, index);
        }
    }

    // FSTORE_0
    public static class FSTORE_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            fstore(frame, 0);
        }
    }

    // FSTORE_1
    public static class FSTORE_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            fstore(frame, 1);
        }
    }

    // FSTORE_2
    public static class FSTORE_2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            fstore(frame, 2);
        }
    }

    // FSTORE_3
    public static class FSTORE_3 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            fstore(frame, 3);
        }
    }

    // dstore_x操作通用函数
    private static void dstore(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        localVars.setDouble(index, stack.popDouble());
    }

    // DSTORE
    public static class DSTORE extends U1IndexInstruction {
        @Override
        public void execute(Frame frame) {
            int index = getIndex();
            dstore(frame, index);
        }
    }

    // DSTORE_0
    public static class DSTORE_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            dstore(frame, 0);
        }
    }

    // DSTORE_1
    public static class DSTORE_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            dstore(frame, 1);
        }
    }

    // DSTORE_2
    public static class DSTORE_2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            dstore(frame, 2);
        }
    }

    // DSTORE_3
    public static class DSTORE_3 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            dstore(frame, 3);
        }
    }

    // astore_x操作通用函数
    private static void astore(Frame frame, int index) {
        LocalVars localVars = frame.getLocalVars();
        OPStack stack = frame.getOpStack();
        localVars.setRef(index, stack.popRef());
    }

    // ASTORE
    public static class ASTORE extends U1IndexInstruction {
        @Override
        public void execute(Frame frame) {
            int index = getIndex();
            astore(frame, index);
        }
    }

    // ASTORE_0
    public static class ASTORE_0 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            astore(frame, 0);
        }
    }

    // ASTORE_1
    public static class ASTORE_1 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            astore(frame, 1);
        }
    }

    // ASTORE_2
    public static class ASTORE_2 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            astore(frame, 2);
        }
    }

    // ASTORE_3
    public static class ASTORE_3 extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            astore(frame, 3);
        }
    }
}
