package com.lilhui.jvm.rtda;

import com.lilhui.jvm.rtda.heap.Object;
import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/10 16:39
 */
@Data
public class Slot {
    long num;
    Object ref;

    public Slot dupSlot() {
        Slot dumpSlot = new Slot();
        dumpSlot.setNum(this.getNum());
        dumpSlot.setRef(this.getRef());
        return dumpSlot;
    }
}
