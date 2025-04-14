package com.lilhui.jvm.interpreter;

import com.lilhui.jvm.classfile.ClazzLoader;
import com.lilhui.jvm.instructions.Instructions;
import com.lilhui.jvm.instructions.base.CodeReader;
import com.lilhui.jvm.instructions.base.Instruction;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.Thread;
import com.lilhui.jvm.rtda.heap.Object;
import com.lilhui.jvm.rtda.heap.*;

import java.util.Arrays;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/12 11:30
 */
public class Interpreter {

    public static void interpret(Method method, boolean logInst, String[] args) {
        Thread thread = new Thread();
        Frame frame = thread.newFrame(method);
        thread.pushFrame(frame);
        Object jvmArgs = createArgsArray(method.getClazz().getLoader(), args);
        frame.getLocalVars().setRef(0, jvmArgs);
        try {
            run(thread, method.getCode(), logInst);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            catchErr(thread, throwable);
        }
    }

    private static Object createArgsArray(ClazzLoader loader, String[] args) {
        Clazz strClazz = loader.loadClass("java/lang/String");
        ArrayObject argsArr = (ArrayObject) strClazz.getArrayClazz().newArray(args.length);
        Object[] refs = argsArr.refs();
        for (int i=0; i<args.length; i++) {
            refs[i] = StringPool.stringToJvmString(loader, args[i]);
        }
        return argsArr;
    }

    private static void run(Thread thread, byte[] code, boolean logInst) {
        //code是方法的指令集
        CodeReader reader = new CodeReader(code);
        while (true) {
            //先获取当前帧
            Frame frame = thread.currentFrame();
            //获取下一个PC计数器并缓存
            int pc = frame.getNextPC();
            thread.setPc(pc);
            reader.reset(frame.getMethod().getCode(), pc);
            //读取一个字节，操作编码
            int opcode = reader.readU1();
            //通过操作编码获取指令的实现
            Instruction instruction = Instructions.newInstruction(opcode);
            //初始化指令相关的操作数
            instruction.readOperands(reader);
            //设置下一帧的地址
            frame.setNextPC(reader.getPC());
            if (logInst) {
                logInstruction(frame, instruction);
            }
            //执行指令
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
        throw new RuntimeException("end");
    }

    private static void logFrames(Thread thread) {
        while (!thread.isStackEmpty()) {
            Frame frame = thread.popFrame();
            Method method = frame.getMethod();
            String clazzName = method.getClazz().getName();
            System.out.println(">>PC:" + frame.getNextPC() + " " + clazzName + "." + method.getName() + "." + method.getDescriptor());
            System.out.println("LocalVars: " + Arrays.toString(frame.getOpStack().getSlots()));
            System.out.println("OperandStack: " + Arrays.toString(frame.getOpStack().getSlots()));
        }
    }

}
