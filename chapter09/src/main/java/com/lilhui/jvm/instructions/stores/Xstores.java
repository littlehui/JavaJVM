package com.lilhui.jvm.instructions.stores;

import com.lilhui.jvm.instructions.base.NoOperandsInstruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.OPStack;
import com.lilhui.jvm.rtda.heap.ArrayObject;
import com.lilhui.jvm.rtda.heap.Object;

/**
 * TODO
 *
 * @author littlehui
 * @version 1.0
 * @date 2023/06/20 17:41
 */
public class Xstores {

   private static void checkNotNull(Object ref) {
      if (ref == null) {
         throw new NullPointerException();
      }
   }

   private static void checkIndex(int arrayLength, int index) {
      if (index < 0 || index >= arrayLength) {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public static class AASTORE extends NoOperandsInstruction {

      @Override
      public void execute(Frame frame) {
         OPStack opStack = frame.getOpStack();
         Object value = opStack.popRef();
         int index = opStack.popInt();
         ArrayObject arrayRef = (ArrayObject) opStack.popRef();
         checkNotNull(arrayRef);
         Object[] refs = arrayRef.refs();
         checkIndex(refs.length, index);
         refs[index] = value;
      }
   }

   public static class BASTORE extends NoOperandsInstruction {

      @Override
      public void execute(Frame frame) {
         OPStack opStack = frame.getOpStack();
         byte value = (byte) opStack.popInt();
         int index = opStack.popInt();
         ArrayObject arrayRef = (ArrayObject) opStack.popRef();
         checkNotNull(arrayRef);
         byte[] refs = arrayRef.bytes();
         checkIndex(refs.length, index);
         refs[index] = value;
      }
   }

   public static class CASTORE extends NoOperandsInstruction {

      @Override
      public void execute(Frame frame) {
         OPStack opStack = frame.getOpStack();
         char value = (char) opStack.popInt();
         int index = opStack.popInt();
         ArrayObject arrayRef = (ArrayObject) opStack.popRef();
         checkNotNull(arrayRef);
         char[] refs = arrayRef.chars();
         checkIndex(refs.length, index);
         refs[index] = value;
      }
   }

   public static class DASTORE extends NoOperandsInstruction {

      @Override
      public void execute(Frame frame) {
         OPStack opStack = frame.getOpStack();
         double value = (double) opStack.popInt();
         int index = opStack.popInt();
         ArrayObject arrayRef = (ArrayObject) opStack.popRef();
         checkNotNull(arrayRef);
         double[] refs = arrayRef.doubles();
         checkIndex(refs.length, index);
         refs[index] = value;
      }
   }

   public static class FASTORE extends NoOperandsInstruction {

      @Override
      public void execute(Frame frame) {
         OPStack opStack = frame.getOpStack();
         float value = (float) opStack.popInt();
         int index = opStack.popInt();
         ArrayObject arrayRef = (ArrayObject) opStack.popRef();
         checkNotNull(arrayRef);
         float[] refs = arrayRef.floats();
         checkIndex(refs.length, index);
         refs[index] = value;
      }
   }

   public static class IASTORE extends NoOperandsInstruction {

      @Override
      public void execute(Frame frame) {
         OPStack opStack = frame.getOpStack();
         int value = (int) opStack.popInt();
         int index = opStack.popInt();
         ArrayObject arrayRef = (ArrayObject) opStack.popRef();
         checkNotNull(arrayRef);
         int[] refs = arrayRef.ints();
         checkIndex(refs.length, index);
         refs[index] = value;
      }
   }

   public static class LASTORE extends NoOperandsInstruction {

      @Override
      public void execute(Frame frame) {
         OPStack opStack = frame.getOpStack();
         long value = (long) opStack.popInt();
         int index = opStack.popInt();
         ArrayObject arrayRef = (ArrayObject) opStack.popRef();
         checkNotNull(arrayRef);
         long[] refs = arrayRef.longs();
         checkIndex(refs.length, index);
         refs[index] = value;
      }
   }

   public static class SASTORE extends NoOperandsInstruction {

      @Override
      public void execute(Frame frame) {
         OPStack opStack = frame.getOpStack();
         short value = (short) opStack.popInt();
         int index = opStack.popInt();
         ArrayObject arrayRef = (ArrayObject) opStack.popRef();
         checkNotNull(arrayRef);
         short[] refs = arrayRef.shorts();
         checkIndex(refs.length, index);
         refs[index] = value;
      }
   }
}
