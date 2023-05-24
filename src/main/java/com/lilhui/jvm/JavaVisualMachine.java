package com.lilhui.jvm;

import com.lilhui.jvm.cmd.Command;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/23 15:01
 */
public class JavaVisualMachine {

    public static void main(String[] args) {
        Command command = Command.parseCmd(args);
        if (command.getVersionFlag()) {
            System.out.println("version 0.0.1");
        } else if (command.getHelpFlag() || command.getClazz() == null) {
            System.out.println(command.usage());
        } else {
            startJVM(command);
        }
    }

    public static void startJVM(Command command) {
        String printStr = String.format("classpath:%s class:%s args:%v\n",
                command.getCpOption(), command.getClazz(), command.getArgs());
        System.out.println(printStr);
    }
}
