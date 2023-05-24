package com.lilhui.jvm.cmd;

import com.lilhui.jvm.JavaVisualMachine;

import java.util.Arrays;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/5/23 14:57
 */
public class Command {

    private Boolean helpFlag = false;

    private Boolean versionFlag = false;

    private String cpOption = "";

    private String clazz = null;

    private String[] args;

    public Boolean getHelpFlag() {
        return helpFlag;
    }

    public void setHelpFlag(Boolean helpFlag) {
        this.helpFlag = helpFlag;
    }

    public Boolean getVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(Boolean versionFlag) {
        this.versionFlag = versionFlag;
    }

    public String getCpOption() {
        return cpOption;
    }

    public void setCpOption(String cpOption) {
        this.cpOption = cpOption;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public static Command parseCmd(String[] originArgs) {
        Command command = new Command();
        StringBuilder stringBuilder = new StringBuilder();
        Integer optionCount = 0;
        if (originArgs != null && originArgs.length > 0) {
            for (String arg : originArgs) {
                switch (arg) {
                    case CommandConstant.OPTION_HELP_SHOT:
                    case CommandConstant.OPTION_HELP:
                        stringBuilder.append("print help message");
                        optionCount++;
                        command.setHelpFlag(true);
                        break;
                    case CommandConstant.OPTION_CP:
                    case CommandConstant.OPTION_CP_SHOT:
                        stringBuilder.append("classpath");
                        optionCount++;
                        command.setCpOption("classpath");
                        break;
                    case CommandConstant.OPTION_VERSION:
                        stringBuilder.append("version");
                        optionCount++;
                        command.setVersionFlag(true);
                        break;
                }
            }
        }
        if (originArgs.length > optionCount) {
            String clazz = originArgs[optionCount];
            command.setClazz(clazz);
            if (originArgs.length > optionCount + 1) {
                String[] args = Arrays.copyOfRange(originArgs, optionCount + 1, originArgs.length);
                command.setArgs(args);
            }
        }
        return command;
    }

    public String usage() {
        return String.format("Usage:java %s [-options] class [args...]\n", JavaVisualMachine.class.getResource("") + JavaVisualMachine.class.getName());
    }

}
