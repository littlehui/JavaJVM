## 说明

实现了INVOKE_XX指令和xxRETURN指令，对解释器进行了改造，可以支持方法调用。

## 遇到的问题

1. 在几个RETURN指令，中会涉及调用thread.topFrame();这个是调用stack.top()。如果调用stack.pop()，会导致fram过早弹出，返回到前前帧。前帧余下的指令不会被执行。
2. INVOKE_SPECIAL中的getRefFromTop参数必须用method.getArgSlotCount() - 1。因为是获取topRef。
3. 在解释器中执行方法调用。在frame入栈后。相应的 执行代码 code需要跟心，必须是frame.getMethod().getCode()。
4. InterfaceMethodRef的name和descriptor需要从nameAndType字段进行拆分与MethodRef一样。

## 执行过程

```
-verbose -cp "/Users/littlehui/WorkSpaces/Home/jvm/JavaJVM/chapter07/target/classes" com.lilhui.jvm.FibonacciTest

/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/bin/java -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:54556,suspend=y,server=n -javaagent:/Users/littlehui/Library/Caches/JetBrains/IntelliJIdea2021.2/captureAgent/debugger-agent.jar -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/javaws.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/jfxswt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/plugin.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/lib/ant-javafx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/lib/javafx-mx.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/lib/packager.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_311.jdk/Contents/Home/lib/tools.jar:/Users/littlehui/WorkSpaces/Home/jvm/JavaJVM/chapter07/target/classes:/Users/littlehui/Software/repository/org/projectlombok/lombok/1.18.22/lombok-1.18.22.jar:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar com.lilhui.jvm.Jvm -verbose -cp /Users/littlehui/WorkSpaces/Home/jvm/JavaJVM/chapter07/target/classes com.lilhui.jvm.FibonacciTest
Connected to the target VM, address: '127.0.0.1:54556', transport: 'socket'
[Loaded java/lang/Object]
[Loaded com/lilhui/jvm/FibonacciTest]
PC:0 com/lilhui/jvm/FibonacciTest.main.([Ljava/lang/String;)Vclass com.lilhui.jvm.instructions.constants.Constants$LDC2_W
PC:3 com/lilhui/jvm/FibonacciTest.main.([Ljava/lang/String;)Vclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:8 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:9 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:10 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:11 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:8 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:9 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:10 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:11 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:8 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:9 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:10 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:11 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:8 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:9 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:10 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:11 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:6 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:7 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:14 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:15 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LDC2_W
PC:18 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:19 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:6 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:7 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:22 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LADD
PC:23 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:14 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:15 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LDC2_W
PC:18 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:19 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:6 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:7 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:22 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LADD
PC:23 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:14 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:15 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LDC2_W
PC:18 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:19 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:8 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:9 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:10 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:11 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:6 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:7 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:14 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:15 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LDC2_W
PC:18 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:19 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:6 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:7 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:22 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LADD
PC:23 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:22 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LADD
PC:23 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:14 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:15 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LDC2_W
PC:18 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:19 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:8 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:9 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:10 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:11 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:8 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:9 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:10 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:11 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:6 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:7 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:14 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:15 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LDC2_W
PC:18 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:19 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:6 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:7 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:22 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LADD
PC:23 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:14 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:15 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LDC2_W
PC:18 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LSUB
PC:19 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.references.INVOKE_STATIC
PC:0 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:1 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.constants.Constants$LCONST_1
PC:2 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$LCMP
PC:3 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.comparisons.Comparisons$IFGT
PC:6 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_0
PC:7 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:22 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LADD
PC:23 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:22 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.math.MathOperations$LADD
PC:23 com/lilhui/jvm/FibonacciTest.fibonacci.(J)Jclass com.lilhui.jvm.instructions.control.Controls$LRETURN
PC:6 com/lilhui/jvm/FibonacciTest.main.([Ljava/lang/String;)Vclass com.lilhui.jvm.instructions.stores.Stores$LSTORE_1
PC:7 com/lilhui/jvm/FibonacciTest.main.([Ljava/lang/String;)Vclass com.lilhui.jvm.instructions.references.GET_STATIC
[Loaded java/lang/System]
PC:10 com/lilhui/jvm/FibonacciTest.main.([Ljava/lang/String;)Vclass com.lilhui.jvm.instructions.loads.Loads$LLOAD_1
PC:11 com/lilhui/jvm/FibonacciTest.main.([Ljava/lang/String;)Vclass com.lilhui.jvm.instructions.references.INVOKE_VIRTUAL
[Loaded java/lang/AutoCloseable]
[Loaded java/io/Closeable]
[Loaded java/io/Flushable]
[Loaded java/io/OutputStream]
[Loaded java/io/FilterOutputStream]
[Loaded java/lang/Appendable]
[Loaded java/io/PrintStream]
5
PC:14 com/lilhui/jvm/FibonacciTest.main.([Ljava/lang/String;)Vclass com.lilhui.jvm.instructions.control.Controls$RETURN
Disconnected from the target VM, address: '127.0.0.1:54556', transport: 'socket'

Process finished with exit code 0

```