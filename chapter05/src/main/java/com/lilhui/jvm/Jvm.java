package com.lilhui.jvm;

import com.lilhui.jvm.classfile.ClassFile;
import com.lilhui.jvm.classfile.Method;
import com.lilhui.jvm.classpath.ClassPath;
import com.lilhui.jvm.cmd.Cmd;
import com.lilhui.jvm.interpreter.Interpreter;
import com.lilhui.jvm.rtda.Frame;
import com.lilhui.jvm.rtda.LocalVars;
import com.lilhui.jvm.rtda.OPStack;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 17:09
 */
public class Jvm {
    public static void main(String[] args) {
        Cmd cmd = Cmd.parseCmd(args);
        if (cmd.isVersionFlag()) {
            System.out.println("version 0.0.1");
        } else if (cmd.isHelpFlag() || cmd.getClassPath() == null) {
            Cmd.printUsage();
        } else {
            startJVM(cmd);
        }
    }


    private static void startJVM(Cmd cmd) {
        byte[] bytecode = getClassByteCode(cmd);
        ClassFile classFile = parseByteCode(bytecode);
        Method mainMethod = getMainMethod(classFile);
        Interpreter.interpret(mainMethod);
    }

    private static byte[] getClassByteCode(Cmd cmd) {
        ClassPath classPath = ClassPath.parse(cmd.getXjreOption(), cmd.getCpOption());
        String className = cmd.getClassPath().replace(".", "/");
        byte[] data = new byte[0];
        try {
            data = classPath.readClass(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            throw new RuntimeException("类文件不存在");
        }
        return data;
    }

    private static ClassFile parseByteCode(byte[] bytecode) {
        ClassFile classFile = ClassFile.parseBytecode(bytecode);
        if (classFile == null) {
            throw new RuntimeException("类文件内容格式错误");
        }
        return classFile;
    }

    private static Method getMainMethod(ClassFile classFile) {
        for (Method method : classFile.getMethods()) {
            if (method.getName().equals("main")) {
                return method;
            }
        }
        return null;
    }

}
