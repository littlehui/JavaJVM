package com.lilhui.jvm;

import com.lilhui.jvm.classfile.*;
import com.lilhui.jvm.classpath.ClassPath;
import com.lilhui.jvm.cmd.Cmd;
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
        startJVM(cmd);
    }
    public static void startJVM(Cmd cmd) {
        Frame frame = new Frame(100, 100);
        testLocalVars(frame.getLocalVars());
        testOperandStack(frame.getOpStack());
    }

    public static void testLocalVars(LocalVars vars) {
        vars.setInt(0, 100);
        vars.setInt(1, -100);
        vars.setLong(2, 2997924580L);
        vars.setLong(4, -2997924580L);
        vars.setFloat(6, 3.1415926f);
        vars.setDouble(7, 2.71828182845);
        vars.setRef(9, null);
        System.out.println(vars.getInt(0));      // 100
        System.out.println(vars.getInt(1));      // -100
        System.out.println(vars.getLong(2));     // 2997924580
        System.out.println(vars.getLong(4));     // -2997924580
        System.out.println(vars.getFloat(6));    // 3.1415926
        System.out.println(vars.getDouble(7));   // 2.71828182845
        System.out.println(vars.getRef(9));      // null
    }

    public static void testOperandStack(OPStack ops) {
        ops.pushInt(100);
        ops.pushInt(-100);
        ops.pushLong(2997924580L);
        ops.pushLong(-2997924580L);
        ops.pushFloat(3.1415926f);
        ops.pushDouble(2.71828182845);
        ops.pushRef(null);
        System.out.println(ops.popRef());
        System.out.println(ops.popDouble());
        System.out.println(ops.popFloat());
        System.out.println(ops.popLong());
        System.out.println(ops.popLong());
        System.out.println(ops.popInt());
        System.out.println(ops.popInt());
    }
}
