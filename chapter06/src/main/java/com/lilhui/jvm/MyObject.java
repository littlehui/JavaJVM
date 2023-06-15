package com.lilhui.jvm;

/**
 * @author littlehui
 * @version 1.0
 * @description TODO
 * @date 2023/6/15 13:59
 */
public class MyObject {

    public static int staticVar;

    public int instanceVar;

    public static void main(String[] args) {
        int x = 32768; // ldc
        MyObject myObj = new MyObject(); // new
        MyObject.staticVar = x; // putstatic
        x = MyObject.staticVar; // getstatic
        myObj.instanceVar = x; // putfield
        x = myObj.instanceVar; // getfield
        myObj.instanceVar--;
        Object obj = myObj;
        if (obj instanceof MyObject) { // instanceof
            myObj = (MyObject) obj; // checkcast
            System.out.println(myObj.instanceVar);
        }
    }
}
