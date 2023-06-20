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

    private java.lang.Object data;

    public Slots getFields() {
        return (Slots)data;
    }

    public Object(Clazz clazz) {
        this.clazz = clazz;
        this.data = new Slots(clazz.getInstanceSlotCount());
    }

    public Object(Clazz clazz, java.lang.Object data) {
        this.data = data;
        this.clazz = clazz;
    }

    public boolean isInstanceOf(Clazz clazz) {
        return this.getClazz().isAssignableFrom(clazz);
    }
}