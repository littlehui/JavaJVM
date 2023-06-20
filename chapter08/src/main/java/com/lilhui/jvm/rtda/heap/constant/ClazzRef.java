package com.lilhui.jvm.rtda.heap.constant;

import com.lilhui.jvm.classfile.constant.ConstantClassInfo;
import com.lilhui.jvm.rtda.heap.ConstantPool;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/13 17:16
 */
public class ClazzRef extends SymRef {

    public ClazzRef(ConstantPool cp, ConstantClassInfo classInfo) {
        super();
        setConstantPool(cp);
        setClassName(classInfo.getClassName());
    }

}
