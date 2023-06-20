package com.lilhui.jvm.rtda.heap;

import lombok.Getter;
import lombok.Setter;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/16 17:03
 */
@Getter
@Setter
public class MethodDescriptor {

    private String[] parameterTypes;

    private String returnTye;
}
