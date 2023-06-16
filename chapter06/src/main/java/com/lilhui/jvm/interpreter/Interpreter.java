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

    public static void interpret(Method method) {
        Thread thread = new Thread();
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);
        try {
            run(thread, method.getCode());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //catchErr(frame, throwable);
        }
    }

    private static void run(Thread thread, byte[] code) {
        Frame frame = thread.popFrame();
        CodeReader reader = new CodeReader(code);
        while (true) {
            int pc = frame.getNextPC();
            thread.setPc(pc);
            reader.reset(code, pc);
            int opcode = reader.readU1();
            Instruction instruction = Instructions.newInstruction(opcode);
            instruction.readOperands(reader);
            frame.setNextPC(reader.getPC());
            System.out.println("pc:" + pc + "," + instruction.getClass().toString());
            instruction.execute(frame);
        }
    }

    private static void catchErr(Frame frame, Throwable throwable) {
        System.out.println("LocalVars: " + Arrays.toString(frame.getLocalVars().getSlots()));
        System.out.println("OperandStack: " + Arrays.toString(frame.getOpStack().getSlots()));
        throw new RuntimeException("end");
    }

}
