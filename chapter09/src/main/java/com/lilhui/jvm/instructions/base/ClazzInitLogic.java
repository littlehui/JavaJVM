package com.lilhui.jvm.instructions.base;

import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.Thread;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.Method;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 10:36
 */
public class ClazzInitLogic {

   public void initClass(Thread thread, Clazz clazz) {
      clazz.startInit();
      scheduleClinit(thread, clazz);
      initSuperClazz(thread, clazz);
   }

   private void initSuperClazz(Thread thread, Clazz clazz) {
      if (!clazz.isInterface()) {
         Clazz superClazz = clazz.getSuperClazz();
         if (superClazz != null && !superClazz.isInitializationFlag()) {
            initClass(thread, superClazz);
         }
      }
   }

   private void scheduleClinit(Thread thread, Clazz clazz) {
      Method clinit = clazz.getClinitMethod();
      if (clinit != null) {
         //执行 <clinit>
         Frame newFrame = thread.newFrame(clinit);
         thread.pushFrame(newFrame);
      }
   }
}
