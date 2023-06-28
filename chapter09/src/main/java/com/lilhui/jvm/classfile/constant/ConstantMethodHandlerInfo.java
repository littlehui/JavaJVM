package com.lilhui.jvm.classfile.constant;

import com.lilhui.jvm.classfile.BytecodeReader;
import lombok.Getter;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/21 21:42
 */
public class ConstantMethodHandlerInfo implements ConstantInfo {

   @Getter
   private int referenceKind;

   @Getter
   private int referenceIndex;

   @Override
   public void readInfo(BytecodeReader reader) {
      this.referenceKind = reader.readU1();
      this.referenceIndex = reader.readU2();
   }

   @Override
   public void printInfo() {

   }
}
