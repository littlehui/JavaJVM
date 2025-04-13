# 类解析实现

```shell
java -cp "chapter03/target/classes" com.lilhui.jvm.Jvm -cp "chapter03/target/classes" com.lilhui.jvm.HellWorld
```

```shell
版本：52.0
Constant pool: 34
#1 = Methodref          #6:#20          java/lang/Object.<init>()V
#2 = Fieldref           #21:#22         java/lang/System.outLjava/io/PrintStream;
#3 = String             #23             Hello World
#4 = Methodref          #24:#25         java/io/PrintStream.println(Ljava/lang/String;)V
#5 = Class              #26             com/lilhui/jvm/HelloWorld
#6 = Class              #27             java/lang/Object
#7 = Utf8               <init>
#8 = Utf8               ()V
#9 = Utf8               Code
#10 = Utf8              LineNumberTable
#11 = Utf8              LocalVariableTable
#12 = Utf8              this
#13 = Utf8              Lcom/lilhui/jvm/HelloWorld;
#14 = Utf8              main
#15 = Utf8              ([Ljava/lang/String;)V
#16 = Utf8              args
#17 = Utf8              [Ljava/lang/String;
#18 = Utf8              SourceFile
#19 = Utf8              HelloWorld.java
#20 = NameAndType               #7:8            <init>:()V
#21 = Class             #28             java/lang/System
#22 = NameAndType               #29:30          out:Ljava/io/PrintStream;
#23 = Utf8              Hello World
#24 = Class             #31             java/io/PrintStream
#25 = NameAndType               #32:33          println:(Ljava/lang/String;)V
#26 = Utf8              com/lilhui/jvm/HelloWorld
#27 = Utf8              java/lang/Object
#28 = Utf8              java/lang/System
#29 = Utf8              out
#30 = Utf8              Ljava/io/PrintStream;
#31 = Utf8              java/io/PrintStream
#32 = Utf8              println
#33 = Utf8              (Ljava/lang/String;)V
flags: 33
this_class: #5
Class           #26             com/lilhui/jvm/HelloWorld
super_class: #6
Class           #27             java/lang/Object
interfaces: 0
fields: 0
methods: 2
#0
access_flags: 1
name_index: #7          <init>
descriptor_index: #8            ()V
attributes: #1
#0{Code:
        stack=1, locals=1
        code: [42, -73, 0, 1, -79]
        exception_table: 0
attributes: 2
        #0{
LineNumberTable:
        start_pc: 0
        line_number: 10
}
        #1{
LocalVariableTable:
        start_pc: 0
        length: 5
        name_index: 12
        descriptor_index: 13
        index: 0
}
}
#1
access_flags: 9
name_index: #14         main
descriptor_index: #15           ([Ljava/lang/String;)V
attributes: #1
#0{Code:
        stack=2, locals=1
        code: [-78, 0, 2, 18, 3, -74, 0, 4, -79]
        exception_table: 0
attributes: 2
        #0{
LineNumberTable:
        start_pc: 0
        line_number: 13
        start_pc: 8
        line_number: 14
}
        #1{
LocalVariableTable:
        start_pc: 0
        length: 9
        name_index: 16
        descriptor_index: 17
        index: 0
}
}
attributes: 1
#0{
SourceFile:             #19
}
```