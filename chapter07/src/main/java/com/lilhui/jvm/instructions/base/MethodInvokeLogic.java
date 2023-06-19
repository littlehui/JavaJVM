package com.lilhui.jvm.instructions.base;

import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.Slot;
import com.lilhui.jvm.rtda.heap.Method;
import com.lilhui.jvm.rtda.Thread;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/16 16:56
 */
public class MethodInvokeLogic {

    public void invokeMethod(Frame invokerFrame, Method method) {
        Thread thread = invokerFrame.getThread();
        Frame newFrame = thread.newFrame(method);
        thread.pushFrame(newFrame);
        int argsSlotsCount = method.getArgSlotCount();
        if (argsSlotsCount > 0) {
            for (int i=argsSlotsCount - 1; i>=0; i--) {
                Slot slot = invokerFrame.getOpStack().popSlot();
                newFrame.getLocalVars().setSlot(i, slot);
            }
        }
        //本地方法不支持
        if (method.isNative()) {
            if (method.getName().equals("registerNatives")) {
                thread.popFrame();
            } else {
                //暂不支持本地方法
                throw new RuntimeException("不支持本地方法" + method.getClazz() + "." + method.getName());
            }
        }
    }
}
