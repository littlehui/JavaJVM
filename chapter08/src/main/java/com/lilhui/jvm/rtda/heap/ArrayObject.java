package com.lilhui.jvm.rtda.heap;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 12:38
 */
@Getter
@Setter
public class ArrayObject extends Object {

   public ArrayObject(Clazz clazz) {
      super(clazz);
   }

   public ArrayObject(Clazz clazz, java.lang.Object data) {
      super(clazz, data);
   }

   public byte[] bytes() {
      return (byte[]) getData();
   }

   public short[] shorts() {
      return (short[]) getData();
   }

   public int[] ints() {
      return (int[]) getData();
   }

   public long[] longs() {
      return (long[]) getData();
   }

   public char[] chars() {
      return (char[]) getData();
   }

   public float[] floats() {
      return (float[]) getData();
   }

   public double[] doubles() {
      return (double[]) getData();
   }

   public Object[] refs() {
      return (Object[]) getData();
   }

   public java.lang.Object refObj() {
      return getData();
   }

   public int arrayLength() {
      if (this.getData() instanceof byte[]) {
         return ((byte[]) this.getData()).length;
      } else if (this.getData() instanceof short[]) {
         return ((short[]) this.getData()).length;
      } else if (this.getData() instanceof int[]) {
         return ((int[]) this.getData()).length;
      } else if (this.getData() instanceof long[]) {
         return ((long[]) this.getData()).length;
      } else if (this.getData() instanceof char[]) {
         return ((char[]) this.getData()).length;
      } else if (this.getData() instanceof float[]) {
         return ((float[]) this.getData()).length;
      } else if (this.getData() instanceof double[]) {
         return ((double[]) this.getData()).length;
      } else if (this.getData() instanceof Object[]) {
         return ((Object[]) this.getData()).length;
      } else {
         throw new RuntimeException("Not array!");
      }
   }
}

