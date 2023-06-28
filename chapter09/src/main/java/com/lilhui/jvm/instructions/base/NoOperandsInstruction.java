package com.lilhui.jvm.instructions.base;

import lombok.ToString;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 16:43
 */
@ToString
public abstract class NoOperandsInstruction implements Instruction {

    @Override
    public void readOperands(CodeReader reader) {
        // No operands to read
    }


}
