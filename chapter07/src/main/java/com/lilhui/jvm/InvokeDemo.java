package com.lilhui.jvm;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/19 12:45
 */
public class InvokeDemo implements Runnable {

    public static void main(String[] args) {
        new InvokeDemo().test();//INVOKE_SPECIAL
    }

    public void test() {
        InvokeDemo.staticMethod(); // invokestatic
        InvokeDemo demo = new InvokeDemo(); // invokespecial
        demo.instanceMethod(); // invokespecial
        //super.equals(null); // invokespecial
        this.run(); // invokevirtual
        ((Runnable) demo).run(); // invokeinterface
    }

    public static void staticMethod() {
        System.out.println(-1);
    }
    private void instanceMethod() {
        System.out.println(0);
    }

    @Override
    public void run() {
        System.out.println(1);
    }
}
