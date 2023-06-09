package com.lilhui.jvm.cmd;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lilhui.jvm.cmd.CmdConstant.*;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/24 16:53
 */
@Data
public class Cmd {
    private boolean helpFlag;
    private boolean versionFlag;
    private String cpOption;
    private String classPath;
    private List<String> args;
    private String xjreOption;

    public static Cmd parseCmd(String[] args) {
        Cmd cmd = new Cmd();
        List<String> arguments = new ArrayList<>(Arrays.asList(args));

        for (int i = 0; i < arguments.size(); i++) {
            String arg = arguments.get(i);
            switch (arg) {
                case OPTION_HELP:
                case OPTION_HELP_SHOT:
                    cmd.helpFlag = true;
                    break;
                case OPTION_VERSION:
                    cmd.versionFlag = true;
                    break;
                case OPTION_CP:
                case OPTION_CP_SHOT:
                    if (i + 1 < arguments.size()) {
                        cmd.cpOption = arguments.get(i + 1);
                        i++;
                    } else {
                        throw new IllegalArgumentException("Missing classpath argument");
                    }
                    break;
                case OPTION_X_JRE:
                    if (i + 1 < arguments.size()) {
                        cmd.xjreOption = arguments.get(i + 1);
                        i++;
                    } else {
                        throw new IllegalArgumentException("Missing jre option argument");
                    }
                    break;
                default:
                    cmd.classPath = arg;
                    cmd.args = arguments.subList(i + 1, arguments.size());
                    return cmd;
            }
        }

        return cmd;
    }

    public static void printUsage() {
        System.out.printf("Usage: %s [-options] class [args...]\n", System.getProperty("sun.java.command"));
    }
}
