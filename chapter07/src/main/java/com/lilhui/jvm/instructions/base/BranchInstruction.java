package com.lilhui.jvm.instructions.base;

import com.lilhui.jvm.rtda.Frame;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 16:43
 */
@Data
public class BranchInstruction implements Instruction {

    protected short offset;

    @Override
    public void readOperands(CodeReader reader) {
        this.offset = reader.readI2();
    }

    @Override
    public void execute(Frame frame) {
        // Execute instruction logic
    }
}
