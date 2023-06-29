package com.lilhui.jvm.rtda.heap;

import lombok.Data;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/9 15:24
 */
@Data
public class Object implements Cloneable {
    // Fields and methods of the Object class in Java
    // ...
    private Clazz clazz;

    private java.lang.Object data;

    private java.lang.Object extra;

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

    public void setRefChar(String name, String descriptor, Object ref) {
        Field field = this.getClazz().getField(name, descriptor, false);
        Slots slots = (Slots)this.getData();
        slots.setRef(field.getSlotId(), ref);
    }

    public Object getRefChar(String name, String descriptor) {
        Field field = this.getClazz().getField(name, descriptor, false);
        Slots slots = (Slots)this.getData();
        Object ref = slots.getRef(field.getSlotId());
        return ref;
    }

    @Override
    public clone() {
        return this.clone();
    }
}