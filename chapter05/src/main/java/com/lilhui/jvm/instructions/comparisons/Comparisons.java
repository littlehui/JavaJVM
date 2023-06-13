package com.lilhui.jvm.instructions.comparisons;

import com.lilhui.jvm.instructions.base.BranchInstruction;
import com.lilhui.jvm.instructions.base.BranchLogic;
import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 16:50
 */
public class Comparisons {
    
    // LCMP
    public static class LCMP extends NoOperandsInstruction {

        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            long v2 = stack.popLong();
            long v1 = stack.popLong();
            if (v1 > v2) {
                stack.pushInt(1);
            } else if (v1 == v2) {
                stack.pushInt(0);
            } else {
                stack.pushInt(-1);
            }
        }
    }

    // FCMPG
    public static class FCMPG extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            fcmp(frame, true);
        }
    }

    // FCMPL
    public static class FCMPL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            fcmp(frame, false);
        }
    }

    // DCMPG
    public static class DCMPG extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            dcmp(frame, true);
        }
    }

    // DCMPL
    public static class DCMPL extends NoOperandsInstruction {
        @Override
        public void execute(Frame frame) {
            dcmp(frame, false);
        }
    }

    // IFEQ
    public static class IFEQ extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            if (i == 0) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IFNE
    public static class IFNE extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            if (i != 0) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IFLT
    public static class IFLT extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            if (i < 0) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IFLE
    public static class IFLE extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            if (i <= 0) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IFGT
    public static class IFGT extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            if (i > 0) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IFGE
    public static class IFGE extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int i = stack.popInt();
            if (i >= 0) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IF_ICMPEQ
    public static class IF_ICMPEQ extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            if (v1 == v2) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }
    // IF_ICMPNE
    public static class IF_ICMPNE extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            if (v1 != v2) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IF_ICMPGT
    public static class IF_ICMPGT extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            if (v1 > v2) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IF_ICMPGE
    public static class IF_ICMPGE extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            if (v1 >= v2) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IF_ICMPLT
    public static class IF_ICMPLT extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            if (v1 < v2) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IF_ICMPLE
    public static class IF_ICMPLE extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            int v2 = stack.popInt();
            int v1 = stack.popInt();
            if (v1 <= v2) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IF_ACMPEQ
    public static class IF_ACMPEQ extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Object r2 = stack.popRef();
            Object r1 = stack.popRef();
            if (r1 == r2) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // IF_ACMPNE
    public static class IF_ACMPNE extends BranchInstruction {
        @Override
        public void execute(Frame frame) {
            OPStack stack = frame.getOpStack();
            Object r2 = stack.popRef();
            Object r1 = stack.popRef();
            if (r1 != r2) {
                BranchLogic.branch(frame, this.getOffset());
            }
        }
    }

    // fcmp通用函数
    private static void fcmp(Frame frame, boolean gFlag) {
        OPStack stack = frame.getOpStack();
        float v2 = stack.popFloat();
        float v1 = stack.popFloat();
        if (v1 > v2) {
            stack.pushInt(1);
        } else if (v1 == v2) {
            stack.pushInt(0);
        } else if (v1 < v2) {
            stack.pushInt(-1);
        } else if (gFlag) {
            stack.pushInt(1);
        } else {
            stack.pushInt(-1);
        }
    }

    // dcmp通用函数
    private static void dcmp(Frame frame, boolean gFlag) {
        OPStack stack = frame.getOpStack();
        double v2 = stack.popDouble();
        double v1 = stack.popDouble();
        if (v1 > v2) {
            stack.pushInt(1);
        } else if (v1 == v2) {
            stack.pushInt(0);
        } else if (v1 < v2) {
            stack.pushInt(-1);
        } else if (gFlag) {
            stack.pushInt(1);
        } else {
            stack.pushInt(-1);
        }
    }
}
