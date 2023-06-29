package com.lilhui.jvm.nativej.java.lang;

import com.lilhui.jvm.nativej.NativeMethod;
import com.lilhui.jvm.nativej.Registry;
import com.lilhui.jvm.rtda.Frame;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/28 18:04
 */
public class Object {

    public void init() {
        Registry.register("java/lang/Object", "getClass", "()Ljava/lang/Class;", new GetClassNativeMethod());
        Registry.register("java/lang/Object", "hashCode", "()I", new HashCodeNativeMethod());
        Registry.register("java/lang/Object", "clone", "()Ljava/lang/Object;", new CloneNativeMethod());

    }

    class GetClassNativeMethod implements NativeMethod {

        @Override
        public void invoke(Frame frame) throws Exception {
            com.lilhui.jvm.rtda.heap.Object object = frame.getLocalVars().getRef(0);
            frame.getOpStack().pushRef(object.getClazz().getJClazz());
        }
    }

    class HashCodeNativeMethod implements NativeMethod {
        @Override
        public void invoke(Frame frame) throws Exception {
            com.lilhui.jvm.rtda.heap.Object object = frame.getLocalVars().getRef(0);
            int hash = System.identityHashCode(thisObj);
            frame.getOperandStack().pushInt(hash);
        }
    }


    class CloneNativeMethod implements NativeMethod {
        @Override
        public void invoke(Frame frame) throws Exception {
            com.lilhui.jvm.rtda.heap.Object object = frame.getLocalVars().getRef(0);
            Clazz clazz = thisObj.getClazz();
            Clazz cloneableClazz = frame.getMethod().getClazz().getLoader().loadClass("java/lang/Cloneable");
            if (!cloneableClass.isAssignableFrom(clazz)) {
                throw new CloneNotSupportedException();
            }
            frame.getOperandStack().pushRef(object.clone());
        }
    }
}
