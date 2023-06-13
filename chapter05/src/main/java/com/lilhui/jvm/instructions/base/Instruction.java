package com.lilhui.jvm.instructions.base;

import com.lilhui.jvm.rtda.Frame;

public interface Instruction {

    void readOperands(CodeReader reader);

    void execute(Frame frame);
}
