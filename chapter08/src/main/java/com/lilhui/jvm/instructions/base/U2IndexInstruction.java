package com.lilhui.jvm.instructions.base;

import com.lilhui.jvm.rtda.Frame;
import lombok.Getter;
import lombok.Setter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/14 17:18
 */
public abstract class U2IndexInstruction implements Instruction {

    @Getter
    @Setter
    protected short index;

    @Override
    public void readOperands(CodeReader reader) {
        this.index = reader.readU2();
    }

}
