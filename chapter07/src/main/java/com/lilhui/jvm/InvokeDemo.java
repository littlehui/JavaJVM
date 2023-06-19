package com.lilhui.jvm;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/19 12:45
 */
public class InvokeDemo implements Runnable {

    public static void main(String[] args) {
        new InvokeDemo().test();//INVOKE_SPECIAL,NEW
    }

    public void test() {
        InvokeDemo.staticMethod(); // invokestatic
        InvokeDemo demo = new InvokeDemo(); // invokespecial
        demo.instanceMethod(); // invokespecial
        super.equals(null); // invokespecial
        this.run(); // invokevirtual
        ((Runnable) demo).run(); // invokeinterface
    }

    public static void staticMethod() {

    }
    private void instanceMethod() {

    }

    @Override
    public void run() {

    }
}
