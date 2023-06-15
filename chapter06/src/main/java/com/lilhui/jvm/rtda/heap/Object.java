package com.lilhui.jvm.rtda.heap;

import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:24
 */
@Data
public class Object {
    // Fields and methods of the Object class in Java
    // ...
    private Clazz clazz;

    private Slots fileds;

    public boolean isInstanceOf(Clazz clazz) {
        if (this.clazz == clazz) {
            return true;
        }
        //
        if (clazz.isInterface()) {
            return this.clazz.isImplements(clazz);
        } else {
            return this.clazz.isSubClassOf(clazz);
        }
    }

}