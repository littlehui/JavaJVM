# 环境准备

支持java8,并配置了 JAVA_HOME

```bash
java -cp chapter09/target/chapter09-1.0-SNAPSHOT.jar com.lilhui.jvm.Jvm -verbose -cp "chapter09/target/classes" com.lilhui.jvm.BubbleSortTest

java -cp chapter09/target/chapter09-1.0-SNAPSHOT.jar com.lilhui.jvm.Jvm -verbose -cp "chapter09/target/classes" com.lilhui.jvm.AbDemo

```

```shell
java -cp chapter09/target/chapter09-1.0-SNAPSHOT.jar com.lilhui.jvm.Jvm
```
是执行 chapter09-1.0-SNAPSHOT.jar 下的 com.lilhui.jvm.Jvm.main方法。

```shell
-verbose -cp "chapter09/target/classes" com.lilhui.jvm.BubbleSortTest
```
是通过 com.lilhui.jvm.Jvm.main 执行 BubbleSortTest这个字节码，-verbose 打印详情。