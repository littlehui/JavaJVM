## 说明

实现了简单的类加载器，实现了方法区，运行时常量池。增加了方法调用的几个指令。目前可以执行单个方法。

## 遇到的问题
1. 加载类时会递归地加载超类。所有类的超类都是Object，Object有本地方法，在加载本地方法时attributeCode为空。
2. Object的超类是null。
3. 初始化运行时ConstantPool会报 java.lang.StackOverflowError原因是用的lombok重写了toString方法。导致 ContantPool和constant。在初始化ContantPool发生了递归调用。
4. readU1是读取无符号byte。需要用short来表示。

## 执行过程

```
-cp "F:\InteliJ\Works\jvm\MyJvm\my-jvm-java\chapter06\target\classes" com.lilhui.jvm.MyObject

"D:\Program Files\Java\jdk1.8.0_231\bin\java.exe" -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:55334,suspend=y,server=n -javaagent:C:\Users\17173\AppData\Local\JetBrains\IntelliJIdea2020.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\Program Files\Java\jdk1.8.0_231\jre\lib\charsets.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\deploy.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\access-bridge-64.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\cldrdata.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\dnsns.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\jaccess.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\jfxrt.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\localedata.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\nashorn.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\sunec.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\sunjce_provider.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\sunmscapi.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\sunpkcs11.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\ext\zipfs.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\javaws.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\jce.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\jfr.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\jfxswt.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\jsse.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\management-agent.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\plugin.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\resources.jar;D:\Program Files\Java\jdk1.8.0_231\jre\lib\rt.jar;F:\InteliJ\Works\jvm\MyJvm\my-jvm-java\chapter06\target\classes;D:\maven\repository\org\junit\jupiter\junit-jupiter\5.10.0-M1\junit-jupiter-5.10.0-M1.jar;D:\maven\repository\org\junit\jupiter\junit-jupiter-api\5.10.0-M1\junit-jupiter-api-5.10.0-M1.jar;D:\maven\repository\org\opentest4j\opentest4j\1.2.0\opentest4j-1.2.0.jar;D:\maven\repository\org\junit\platform\junit-platform-commons\1.10.0-M1\junit-platform-commons-1.10.0-M1.jar;D:\maven\repository\org\apiguardian\apiguardian-api\1.1.2\apiguardian-api-1.1.2.jar;D:\maven\repository\org\junit\jupiter\junit-jupiter-params\5.10.0-M1\junit-jupiter-params-5.10.0-M1.jar;D:\maven\repository\org\junit\jupiter\junit-jupiter-engine\5.10.0-M1\junit-jupiter-engine-5.10.0-M1.jar;D:\maven\repository\org\junit\platform\junit-platform-engine\1.10.0-M1\junit-platform-engine-1.10.0-M1.jar;D:\maven\repository\org\projectlombok\lombok\1.18.22\lombok-1.18.22.jar;D:\Program Files\JetBrains\IntelliJ IDEA 2020.3.3\lib\idea_rt.jar" com.lilhui.jvm.Jvm -cp F:\InteliJ\Works\jvm\MyJvm\my-jvm-java\chapter06\target\classes com.lilhui.jvm.MyObject
Connected to the target VM, address: '127.0.0.1:55334', transport: 'socket'
[Loaded java/lang/Object]
[Loaded com/lilhui/jvm/MyObject]
pc:0,class com.lilhui.jvm.instructions.constants.Constants$LDC
pc:2,class com.lilhui.jvm.instructions.stores.Stores$ISTORE_1
pc:3,class com.lilhui.jvm.instructions.references.NEW
pc:6,class com.lilhui.jvm.instructions.stack.Stacks$DUP
pc:7,class com.lilhui.jvm.instructions.references.INVOKE_SPECIAL
pc:10,class com.lilhui.jvm.instructions.stores.Stores$ASTORE_2
pc:11,class com.lilhui.jvm.instructions.loads.Loads$ILOAD_1
pc:12,class com.lilhui.jvm.instructions.references.PUT_STATIC
pc:15,class com.lilhui.jvm.instructions.references.GET_STATIC
pc:18,class com.lilhui.jvm.instructions.stores.Stores$ISTORE_1
pc:19,class com.lilhui.jvm.instructions.loads.Loads$ALOAD_2
pc:20,class com.lilhui.jvm.instructions.loads.Loads$ILOAD_1
pc:21,class com.lilhui.jvm.instructions.references.PUT_FIELD
pc:24,class com.lilhui.jvm.instructions.loads.Loads$ALOAD_2
pc:25,class com.lilhui.jvm.instructions.references.GET_FIELD
pc:28,class com.lilhui.jvm.instructions.stores.Stores$ISTORE_1
pc:29,class com.lilhui.jvm.instructions.loads.Loads$ALOAD_2
pc:30,class com.lilhui.jvm.instructions.stack.Stacks$DUP
pc:31,class com.lilhui.jvm.instructions.references.GET_FIELD
pc:34,class com.lilhui.jvm.instructions.constants.Constants$ICONST_1
pc:35,class com.lilhui.jvm.instructions.math.MathOperations$ISUB
pc:36,class com.lilhui.jvm.instructions.references.PUT_FIELD
pc:39,class com.lilhui.jvm.instructions.loads.Loads$ALOAD_2
pc:40,class com.lilhui.jvm.instructions.stores.Stores$ASTORE_3
pc:41,class com.lilhui.jvm.instructions.loads.Loads$ALOAD_3
pc:42,class com.lilhui.jvm.instructions.references.INSTANCE_OF
pc:45,class com.lilhui.jvm.instructions.comparisons.Comparisons$IFEQ
pc:48,class com.lilhui.jvm.instructions.loads.Loads$ALOAD_3
pc:49,class com.lilhui.jvm.instructions.references.CHECK_CAST
pc:52,class com.lilhui.jvm.instructions.stores.Stores$ASTORE_2
pc:53,class com.lilhui.jvm.instructions.references.GET_STATIC
[Loaded java/lang/System]
pc:56,class com.lilhui.jvm.instructions.loads.Loads$ALOAD_2
pc:57,class com.lilhui.jvm.instructions.references.GET_FIELD
pc:60,class com.lilhui.jvm.instructions.references.INVOKE_VIRTUAL
32767
Disconnected from the target VM, address: '127.0.0.1:55334', transport: 'socket'

Process finished with exit code 0
```