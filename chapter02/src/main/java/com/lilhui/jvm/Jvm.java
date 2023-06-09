package com.lilhui.jvm;

import com.lilhui.jvm.classpath.ClassPath;
import com.lilhui.jvm.cmd.Cmd;

import java.io.IOException;
import java.util.Arrays;

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

    public static void startJVM(Cmd cmd) {
        ClassPath cp = ClassPath.parse(cmd.getXjreOption(), cmd.getCpOption());
        System.out.printf("classpath:%s class:%s args:%s\n",
                cp, cmd.getClassPath(), Arrays.toString(cmd.getArgs().toArray()));
        String className = cmd.getClassPath().replace(".", "/");
        byte[] classData = null;
        try {
            classData = cp.readClass(className);
        } catch (IOException e) {
            System.out.printf("Could not find or load main class %s\n", cmd.getClassPath());
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("class data:%s\n", Arrays.toString(classData));
    }
}
