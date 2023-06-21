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
public class ConstantMethodTypeInfo implements ConstantInfo {

   @Getter
   private int descriptorIndex;

   @Override
   public void readInfo(BytecodeReader reader) {
      this.descriptorIndex = reader.readU2();
   }

   @Override
   public void printInfo() {

   }
}
