## 性能分析

## jps （输出jvm虚拟机进程信息）
-q （输出进程Id）
-m （main函数启动参数）
-l（输出主类的全名，如jar)
```bash
$ jps -l
1965 com.runcoding.Application
```
-v（输出虚拟机进程启动时所带的Jvm参数）
```bash
1964 Launcher -Xmx700m -Djava.awt.headless=true -Djava.endorsed.dirs="" -Djdt.compiler.useSingleThread=true -Dpreload.project.path=/Users/runningghost/projects/soft_develop/github/runcoding.github.io -Dpreload.config.path=/Users/runningghost/Library/Preferences/IntelliJIdea2017.2/options -Dcompile.parallel=false -Drebuild.on.dependency.change=true -Djava.net.preferIPv4Stack=true -Dio.netty.initialSeedUniquifier=-8553886555547395819 -Dfile.encoding=UTF-8 -Djps.file.types.component.name=FileTypeManager -Duser.language=zh -Duser.country=CN -Didea.paths.selector=IntelliJIdea2017.2 -Didea.home.path=/Applications/IntelliJ IDEA.app/Contents -Didea.config.path=/Users/runningghost/Library/Preferences/IntelliJIdea2017.2 -Didea.plugins.path=/Users/runningghost/Library/Application Support/IntelliJIdea2017.2 -Djps.log.dir=/Users/runningghost/Library/Logs/IntelliJIdea2017.2/build-log -Djps.fallback.jdk.home=/Applications/IntelliJ IDEA.app/Contents/jdk/Contents/Home/jre -Djps.fallback.jdk.version=1.8.0_152-release -Dio.netty.noUnsafe=true -Djava.io.tmpdir=/Users/runningghost/Lib
```

## jstat（虚拟机运行监控）
例如:（jstat -gcutil ）gc的统计新老代

参数	作用
-gc	GC堆状态
-gcutil	GC统计汇总
-class	类加载器
-compiler	JIT
-gccapacity	各区大小
-gccause	最近一次GC统计和原因
-gcnew	新区统计
-gcnewcapacity	新区大小
-gcold	老区统计
-gcoldcapacity	老区大小
-gcpermcapacity	永久区大小
-printcompilation	HotSpot编译统计

```bash
$ jstat -gcutil 1965
  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
 95.22   0.00  99.08  36.97  97.84  95.59     18    0.285     2    0.104    0.389
    S0：幸存1区当前使用比例
    S1：幸存2区当前使用比例
    E：伊甸园区使用比例
    O：老年代使用比例
    M：元数据区使用比例
    CCS：压缩使用比例
    YGC：年轻代垃圾回收次数
    FGC：老年代垃圾回收次数
    FGCT：老年代垃圾回收消耗时间
    GCT：垃圾回收消耗总时间
```

```bash
jstat -gc 1
 S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT
6144.0 6144.0  0.0    0.0   336896.0 61118.8   699392.0   25590.4   49792.0 48637.0 6016.0 5672.6     52    0.559   3      0.289    0.849
    S0C：第一个幸存区的大小
    S1C：第二个幸存区的大小
    S0U：第一个幸存区的使用大小
    S1U：第二个幸存区的使用大小
    EC：伊甸园区的大小
    EU：伊甸园区的使用大小
    OC：老年代大小
    OU：老年代使用大小
    MC：方法区大小
    MU：方法区使用大小
    CCSC:压缩类空间大小
    CCSU:压缩类空间使用大小
    YGC：年轻代垃圾回收次数
    YGCT：年轻代垃圾回收消耗时间
    FGC：老年代垃圾回收次数
    FGCT：老年代垃圾回收消耗时间
    GCT：垃圾回收消耗总时间
```

## jinfo (java 配置信息工具)
```bash
jinfo 1965
Attaching to process ID 1965, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.111-b14
Java System Properties:

java.runtime.name = Java(TM) SE Runtime Environment
java.vm.version = 25.111-b14
sun.boot.library.path = /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib
gopherProxySet = false
java.vendor.url = //java.oracle.com/
java.vm.vendor = Oracle Corporation
path.separator = :
java.rmi.server.randomIDs = true
file.encoding.pkg = sun.io
java.vm.name = Java HotSpot(TM) 64-Bit Server VM
sun.os.patch.level = unknown
sun.java.launcher = SUN_STANDARD
user.country = CN
user.dir = /Users/runningghost/projects/soft_develop/github/runcoding.github.io
java.vm.specification.name = Java Virtual Machine Specification
PID = 1965
java.runtime.version = 1.8.0_111-b14
java.awt.graphicsenv = sun.awt.CGraphicsEnvironment
os.arch = x86_64
java.endorsed.dirs = /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/endorsed
org.jboss.logging.provider = slf4j
visualvm.id = 11145166608052
line.separator = 

java.io.tmpdir = /var/folders/8h/pjxlhbtj6215h5b440f4qjkh0000gn/T/
java.vm.specification.vendor = Oracle Corporation
os.name = Mac OS X
sun.jnu.encoding = UTF-8
java.library.path = /Users/runningghost/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.
spring.beaninfo.ignore = true
java.specification.name = Java Platform API Specification
java.class.version = 52.0
sun.management.compiler = HotSpot 64-Bit Tiered Compilers
os.version = 10.13.3
btrace.port = 55847
user.home = /Users/runningghost
user.timezone = Asia/Shanghai
java.awt.printerjob = sun.lwawt.macosx.CPrinterJob
file.encoding = UTF-8
java.specification.version = 1.8
user.name = runningghost
java.vm.specification.version = 1.8
sun.arch.data.model = 64
sun.java.command = com.runcoding.Application
java.home = /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre
user.language = zh
java.specification.vendor = Oracle Corporation
user.language.format = en
awt.toolkit = sun.lwawt.macosx.LWCToolkit
java.vm.info = mixed mode
java.version = 1.8.0_111
java.ext.dirs = /Users/runningghost/Library/Java/Extensions:/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/ext:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java
sun.boot.class.path = /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/sunrsasign.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/classes
java.awt.headless = true
java.vendor = Oracle Corporation
file.separator = /
java.vendor.url.bug = //bugreport.sun.com/bugreport/
sun.io.unicode.encoding = UnicodeBig
sun.cpu.endian = little
sun.cpu.isalist = 

VM Flags:
Non-default VM flags: -XX:CICompilerCount=3 -XX:InitialHeapSize=134217728 -XX:MaxHeapSize=2147483648 -XX:MaxNewSize=715653120 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=44564480 -XX:OldSize=89653248 -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseFastUnorderedTimeStamps -XX:+UseParallelGC 
Command line:  -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:55835,suspend=y,server=n -Dvisualvm.id=11147471070820 -Dvisualvm.id=11145166608052 -Dfile.encoding=UTF-8
```

## jmap(生产堆快照文件)

```bash
$ jmap -dump:format=b,file=/Users/runningghost/Downloads/rundump.bin 1965
Dumping heap to /Users/runningghost/Downloads/rundump.bin ...
Heap dump file created
```
### 打印对象占用情况
```bash
$ jmap -histo 1 > histo.log
$ head -n 10 histo.log
num     #instances         #bytes  class name
----------------------------------------------
   1:        267917       25833816  [C
   2:        161730       18944352  [B
   3:        340711       10902752  java.util.Hashtable$Entry
   4:         49730        6911024  [I
   5:         11329        5806208  [Ljava.util.Hashtable$Entry;
   6:        124306        4505880  [Ljava.lang.Object;
   7:        179739        4313736  java.lang.String
   8:         86947        4173456  java.util.HashMap
   9:        127507        4080224  java.util.HashMap$Node
  10:         27536        2524424  [Ljava.util.HashMap$Node;
```
### 查看堆heap占用情况

```bash
$ jmap -heap 1

using thread-local object allocation.
Parallel GC with 4 thread(s) #垃圾回收的方式

Heap Configuration:
   MinHeapFreeRatio         = 0
   MaxHeapFreeRatio         = 100
   MaxHeapSize              = 1073741824 (1024.0MB)
   NewSize                  = 357564416 (341.0MB)
   MaxNewSize               = 357564416 (341.0MB)
   OldSize                  = 716177408 (683.0MB)
   NewRatio                 = 2
   SurvivorRatio            = 8
   MetaspaceSize            = 21807104 (20.796875MB)
   CompressedClassSpaceSize = 1073741824 (1024.0MB)
   MaxMetaspaceSize         = 17592186044415 MB
   G1HeapRegionSize         = 0 (0.0MB)

Heap Usage:
PS Young Generation
Eden Space:
   capacity = 344981504 (329.0MB)
   used     = 16880448 (16.09844970703125MB)
   free     = 328101056 (312.90155029296875MB)
   4.893145807608283% used
From Space:
   capacity = 6291456 (6.0MB)
   used     = 0 (0.0MB)
   free     = 6291456 (6.0MB)
   0.0% used
To Space:
   capacity = 6291456 (6.0MB)
   used     = 0 (0.0MB)
   free     = 6291456 (6.0MB)
   0.0% used
PS Old Generation
   capacity = 716177408 (683.0MB)
   used     = 26204584 (24.990638732910156MB)
   free     = 689972824 (658.0093612670898MB)
   3.658951498229891% used

23148 interned Strings occupying 2882888 bytes.

```

## jol-cli现实Java对象大小 
- [官网](http://openjdk.java.net/projects/code-tools/jol/)
- [jol-cli-0.9-full.jar下载](http://central.maven.org/maven2/org/openjdk/jol/jol-cli/0.9/jol-cli-0.9-full.jar)

[ObjectSize](../../../base-java/src/main/java/com/runcoding/learn/jvm/ObjectSize.java ':include')

### 查看jdk自带类
```sh
> java -jar jol-cli-0.9-full.jar internals java.util.HashMap
```

## jc-tools
[JCTools 官网](http://jctools.github.io/JCTools/)
JCTools是服务虚拟机并发开发的工具，提供一些JDK没有的并发数据结构辅助开发。


## jstack（用来生成虚拟机线程快照信息，分析线程死锁停顿等）
例如: jstack 2419 
-F （用来生成不被响应时强制生成线程的快照）
-m （堆栈信息）
-l （打印锁信息）
```bash
>$ jstack -l 1965
2018-03-18 16:45:22
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.111-b14 mixed mode):

"Thread-38" #64 daemon prio=9 os_prio=31 tid=0x00007fc8c7ab7800 nid=0x9c23 waiting on condition [0x000070000eb73000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
"Thread-13" #33 daemon prio=9 os_prio=31 tid=0x00007fc8c7809000 nid=0x9e03 runnable [0x000070000f89a000]
   java.lang.Thread.State: RUNNABLE
	at java.net.PlainSocketImpl.socketAccept(Native Method)
```
 
## 类加载冲突（在JVM 启动时加上-verbose:class 可以看到class是从哪个jar中引进来的）

### BTrace 
### 地址
 - 官网地址: https://github.com/btraceio/btrace
 - 教程:    https://www.jianshu.com/p/ee6b5c13c45b
### 安装
 - 下载地址：https://github.com/btraceio/btrace/releases/tag/v1.3.11

```java
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.BTraceUtils;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import com.alibaba.fastjson.JSON;
@BTrace(unsafe = true) // 表示这是一个BTrace跟踪脚本，并启用unsafe模式(因为使用了BTraceUtils以外的方法，即String.valueOf(obj))
public class TracingScript {
    
    @TLS 
    static long beginTime;  
    
     @OnMethod(clazz="com.run.center.order.web.controller.cart.CartController", //跟踪指定的类
              method="incrementCart",//跟踪的方法
              location=@Location(Kind.RETURN))// 表示跟踪某个类的某个方法，位置为方法返回处
    public static void incrementCartfunc(@Self Object self ,
                                         Object param , //请求参数列表(多个参数用多个)
                                         @Return Object result, 
                                         @Duration long durationL ){
        println(strcat(strcat("方法执行时间s:",str(timeMillis()-beginTime)),"ms"));
        println(strcat("当前执行线程名称:", Threads.name(currentThread()))); 
        println(strcat("请求参数:", str(JSON.toJSONString(param))));
        println(strcat("返回结果:", str(JSON.toJSONString(result))));
        println(strcat("方法执行时间:", str(durationL)));
        println("=================================");
    } 
}
```