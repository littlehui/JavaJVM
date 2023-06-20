package com.lilhui.jvm.instructions.base;

import com.lilhui.jvm.rtda.Frame;
import lombok.Getter;
import lombok.Setter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 16:44
 */
public abstract class U1IndexInstruction implements Instruction {

    @Getter
    @Setter
    private short index;

    @Override
    public void readOperands(CodeReader reader) {
        this.index = reader.readU1();
    }

}
