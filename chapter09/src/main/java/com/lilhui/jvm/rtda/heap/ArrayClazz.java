package com.lilhui.jvm.rtda.heap;

import com.lilhui.jvm.classfile.ClassFile;
import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 13:35
 */
@Getter
@Setter
public class ArrayClazz extends Clazz {

    public ArrayClazz(ClassFile cf) {
        super(cf);
    }

    public ArrayClazz() {

    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public Object newArray(int count) {
        if (!this.isArray()) {
            throw new RuntimeException("Not array class: " + this.getName());
        }
        switch (this.getName()) {
            case "[Z":
                return new ArrayObject(this, new byte[count]);
            case "[B":
                return new ArrayObject(this, new byte[count]);
            case "[C":
                return new ArrayObject(this, new char[count]);
            case "[S":
                return new ArrayObject(this, new short[count]);
            case "[I":
                return new ArrayObject(this, new int[count]);
            case "[J":
                return new ArrayObject(this, new long[count]);
            case "[F":
                return new ArrayObject(this, new float[count]);
            case "[D":
                return new ArrayObject(this, new double[count]);
            default:
                return new ArrayObject(this, new Object[count]);
        }
    }





}
