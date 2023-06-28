package com.lilhui.jvm;

import com.lilhui.jvm.classfile.ClazzLoader;
import com.lilhui.jvm.classpath.ClassPath;
import com.lilhui.jvm.cmd.Cmd;
import com.lilhui.jvm.interpreter.Interpreter;
import com.lilhui.jvm.rtda.heap.Clazz;
import com.lilhui.jvm.rtda.heap.Method;

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
        ClassPath classPath = ClassPath.parse(cmd.getXjreOption(), cmd.getCpOption());
        ClazzLoader loader = new ClazzLoader(classPath, cmd.isVerboseFlag());
        String className = cmd.getClassPath().replace(".", "/");
        Clazz clazz = loader.loadClass(className);
        Method mainMethod = getMainMethod(clazz);
        Interpreter.interpret(mainMethod, cmd.isVerboseFlag(), cmd.getArgs());
    }

    private static Method getMainMethod(Clazz clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals("main")) {
                return method;
            }
        }
        return null;
    }

}
