package com.lilhui.jvm;

import com.lilhui.jvm.cmd.Cmd;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/23 15:01
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
        System.out.printf("classpath:%s class:%s args:%s\n",
                cmd.getCpOption(), cmd.getClassPath(), cmd.getArgs());
    }
}
