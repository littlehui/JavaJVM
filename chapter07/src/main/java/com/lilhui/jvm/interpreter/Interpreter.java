package com.lilhui.jvm.interpreter;

import com.lilhui.jvm.classfile.Attributes;
import com.lilhui.jvm.classfile.MethodInfo;
import com.lilhui.jvm.instructions.Instructions;
import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.Instruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.Thread;
import com.lilhui.jvm.rtda.heap.Method;

import java.util.Arrays;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/12 11:30
 */
public class Interpreter {

    public static void interpret(Method method, boolean logInst) {
        Thread thread = new Thread();
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);
        try {
            run(thread, method.getCode(), logInst);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            catchErr(thread, throwable);
        }
    }

    private static void run(Thread thread, byte[] code, boolean logInst) {
        CodeReader reader = new CodeReader(code);
        while (true) {
            Frame frame = thread.currentFrame();
            int pc = frame.getNextPC();
            thread.setPc(pc);
            reader.reset(frame.getMethod().getCode(), pc);
            int opcode = reader.readU1();
            Instruction instruction = Instructions.newInstruction(opcode);
            instruction.readOperands(reader);
            frame.setNextPC(reader.getPC());
            if (logInst) {
                logInstruction(frame, instruction);
            }
            instruction.execute(frame);
            if (thread.isStackEmpty()) {
                break;
            }
        }
    }

    private static void logInstruction(Frame frame, Instruction instruction) {
        Method method = frame.getMethod();
        String clazzName = method.getClazz().getName();
        String methodName = method.getName();
        int pc = frame.getThread().getPc();
        System.out.println("PC:" + pc + " " + clazzName + "." + methodName + "." + method.getDescriptor() + instruction.getClass());
    }

    private static void catchErr(Thread thread, Throwable throwable) {
        logFrames(thread);
/*        System.out.println("LocalVars: " + Arrays.toString(frame.getLocalVars().getSlots()));
        System.out.println("OperandStack: " + Arrays.toString(frame.getOpStack().getSlots()));*/
        throw new RuntimeException("end");
    }

    private static void logFrames(Thread thread) {
        while (!thread.isStackEmpty()) {
            Frame frame = thread.popFrame();
            Method method = frame.getMethod();
            String clazzName = method.getClazz().getName();
            System.out.println(">>PC:" + frame.getNextPC() + " " + clazzName + "." + method.getName() + "." + method.getDescriptor());
        }
    }

}
