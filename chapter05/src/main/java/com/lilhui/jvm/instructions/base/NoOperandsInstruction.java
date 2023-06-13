package com.lilhui.jvm.instructions.base;

import com.lilhui.jvm.rtda.Frame;
import lombok.ToString;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 16:43
 */
@ToString
public class NoOperandsInstruction implements Instruction {

    @Override
    public void readOperands(CodeReader reader) {
        // No operands to read
    }

    @Override
    public void execute(Frame frame) {
        // Execute instruction logic
    }
}
