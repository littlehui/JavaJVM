package com.lilhui.jvm;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/15 13:59
 */
public class MyObject {

    public static double staticVar;

    public double instanceVar;

    public static void main(String[] args) {
        double x = 6100000032768d; // ldc
        MyObject myObj = new MyObject(); // new
        MyObject.staticVar = x; // putstatic
        x = MyObject.staticVar; // getstatic
        myObj.instanceVar = x; // putfield
        x = myObj.instanceVar; // getfield
        Object obj = myObj;
        if (obj instanceof MyObject) { // instanceof
            myObj = (MyObject) obj; // checkcast
            System.out.println(myObj.instanceVar);
        }
    }
}
