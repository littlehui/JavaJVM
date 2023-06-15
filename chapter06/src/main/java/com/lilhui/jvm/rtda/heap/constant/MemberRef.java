package com.lilhui.jvm.rtda.heap.constant;

import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 17:19
 */
@Data
public class MemberRef extends SymRef {
    private String name;
    private String descriptor;

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
    }
}
