package com.lilhui.jvm.cmd;

import com.lilhui.jvm.Jvm;

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
public class Cmd {
    private boolean helpFlag;
    private boolean versionFlag;
    private String cpOption;
    private String classPath;
    private List<String> args;

    public static Cmd parseCmd(String[] args) {
        Cmd cmd = new Cmd();
        List<String> argList = new ArrayList<>(Arrays.asList(args));
        for (int i = 0; i < argList.size(); i++) {
            String arg = argList.get(i);
            switch (arg) {
                case OPTION_HELP:
                case OPTION_HELP_SHOT:
                    cmd.helpFlag = true;
                    argList.remove(i);
                    i--;
                    break;
                case OPTION_VERSION:
                    cmd.versionFlag = true;
                    argList.remove(i);
                    i--;
                    break;
                case OPTION_CP:
                case OPTION_CP_SHOT:
                    cmd.cpOption = argList.get(i + 1);
                    argList.remove(i);
                    argList.remove(i);
                    i--;
                    break;
            }
        }
        if (!argList.isEmpty()) {
            cmd.classPath = argList.get(0);
            cmd.args = argList.subList(1, argList.size());
        }
        return cmd;
    }

    public boolean isHelpFlag() {
        return helpFlag;
    }

    public boolean isVersionFlag() {
        return versionFlag;
    }

    public String getCpOption() {
        return cpOption;
    }

    public String getClassPath() {
        return classPath;
    }

    public List<String> getArgs() {
        return args;
    }

    public static void printUsage() {
        System.out.printf("Usage: %s [-options] class [args...]\n","java " + Jvm.class.getName());
    }
}
