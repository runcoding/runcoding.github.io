### JVM内存模型
<img src='//upload-images.jianshu.io/upload_images/44770-3e7a9bf747d90dbe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240'>
- Java虚拟机规范定义Java内存模型，尝试屏蔽掉各种硬件和操作系统的访问差异；<br>
- JVM内存模型的目标：定义程序中各个变量的访问规则，即在虚拟机中将变量存储到内存和从内存取出来这样的细节；<br>
- volatile关键字：当一个变量用volatile关键字限定后，会有两个语义：<br>
  - `当这个变量的值被修改后，会立即刷新到主内存中，对其他线程可见`；<br>
    当某个线程读取这个变量的时候，也会重新将主内存中的数据刷一份到工作内存中来。<br>
    但是，如果多线程操作这个变量的计算中，后一个值依赖前一个值，就还是会有并发问题，说明volatile不具备原子性；<br>
  - `禁止指令重排`优化，观察voatile变量对应的字节码文件，会发现变量的操作指令后面加了一句lock addl $0x0,(%esp)的操作，这个操作相当于一个内存屏障。<br>
    synchronized关键字：当一个线程对一个变量加锁的时候，就会清空这个变量在当前工作内存中的值，因此该关键字同时满足了可见性和原子性。<br>

### Java运行时数据区
<img src='//upload-images.jianshu.io/upload_images/44770-3dc57436ce2ef1d7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240'>
- `程序计数器（PC）`：Java线程私有，类似于操作系统里的PC计数器，用于指定下一条需要执行的字节码的地址；
- `Java虚拟机栈`：Java线程私有，虚拟机展描述的是Java方法执行的内存模型;<br>
    - 每个方法在执行的时候，都会创建一个栈帧用于存储局部变量、操作数、动态链接、方法出口等信息；<br>
    - 每个方法调用都意味着一个栈帧在虚拟机栈中入栈到出栈的过程；<br>
- `本地方法栈`：和Java虚拟机栈的作用类似，区别是该该区域为JVM调用到的本地方法服务；<br>
- `堆（Heap）`：所有线程共享的一块区域，垃圾收集器管理的主要区域。目前主要的垃圾回收算法都是分代收集，因此该区域还可以细分为如下区域：<br>
   - 年轻代<br>
     -  a. Eden空间
     -  b. From Survivor空间1，From Survivor空间2，用于存储在Young gc过程中幸存的对象；
   - 老年代
- `方法区`：各个线程共享的一个区域，用于存储虚拟机加载的类信息、常量、静态变量等信息；
- `运行时常量池`：方法区的一部分，用于存放编译器生成的各种字面量和符号引用；

### 垃圾收集算法

#### a. 标记-清除算法(基础算法,剩下的都是基于它的不足而进行改进的)
- 标记:标记所有需要回收的对象
- 清除:统一回收所有被标记的对象
- 不足1:效率问题,标记和清除效率都不高;
- 不足2:空间问题,产生大量的不连续的内存碎片

#### b. 复制(Copying)算法
- 内存容量划分两个大小相等的两块,每次使用其中的一块。这块用完了复制存活的对象到另一块,在把这块清理掉
- 不足:代价太高 把内存缩小为原来的一半;
- 现代的商用虚拟机都采用这种算法来回收新生代;<br>
新生代：包括Eden区、From Survivor区、To Survivor区，系统默认大小Eden:Survivor=8:1:1。<br>
老年代：在年轻代中经历了N次垃圾回收后仍然存活的对象，就会被放到老年代中。因此，可以认为老年代中存放的都是一些生命周期较长的对象。<br>

#### c. 标记-整理(Mark-Compact)算法(老年代常用)
- 标记和以前一样,后续步骤不是直接回收,而是存活对象向一端移动,然后清理边界以外的内存; 

#### d. 分代收集算法
- 根据对象存活周期将内存划分不同的几块。一般堆分为新生代和老年代。这样根据年代的特点采用最适当的收集算法
- 新生代:少量存活 选择复制算法
- 老年代:存活率高,没有额外空间担保,必须使用"标记清理"或者"标记整理";

### 虚拟机参数设置
代码的运行参数设置为： -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
<img src="https://runcoding.github.io/static/wiki/learn-java/java/gc_x.png" >

#### 参数详解

 -server: JVM工作在Server模式可以大大提高性能，但应用的启动会比client模式慢大概10%.原因是:当虚拟机运行在-client模式的时候,使用的是一个代号为C1的轻量级编译器, 而-server模式启动的虚拟机采用相对重量级,代号为C2的编译器. C2比C1编译器编译的相对彻底,,服务起来之后,性能更高
 -Xms2560m   初始堆大小
 -Xmx2560m   最大堆大小
 -Xss512k    每个线程栈大小
 -XX:PermSize=128m    设置持久代(perm gen)初始值 物理内存的1/64
 -XX:MaxPermSize=384m 设置持久代最大值
 -XX:NewSize=1536m    设置年轻代大小
 -XX:MaxNewSize=1536m 设置年轻代最大大小
 -XX:SurvivorRatio=22 Eden区与Survivor区的大小比值
 -XX:+PrintGCTimeStamps 改成-XX:+PrintGCDateStamps 将相对时间改成绝对时间
 -XX:-HeapDumpOnOutOfMemoryError改成-XX:+HeapDumpOnOutOfMemoryError    OOM自动导出dump信息
 -XX:-OmitStackTraceInFastThrow  关闭此项优化可以强制打印堆栈。解决NPE没有堆栈信息问题
 -Xloggc:/data/applogs/heap_trace.txt    GClog位置,可以使用在线工具分析  //gceasy.io/
                                         //fastthread.io/   线程分析

-Xms / -Xmx — 堆的初始大小 / 堆的最大大小
-Xmn — 堆中年轻代的大小
-XX:-DisableExplicitGC — 让System.gc()不产生任何作用
-XX:+PrintGCDetails — 打印GC的细节
-XX:+PrintGCDateStamps — 打印GC操作的时间戳
-XX:NewSize / XX:MaxNewSize — 设置新生代大小/新生代最大大小
-XX:NewRatio — 可以设置老生代和新生代的比例
-XX:PrintTenuringDistribution — 设置每次新生代GC后输出幸存者乐园中对象年龄的分布
-XX:InitialTenuringThreshold / -XX:MaxTenuringThreshold：设置老年代阀值的初始值和最大值
-XX:TargetSurvivorRatio：设置幸存区的目标使用率

###  GC安全点(GC会产生停顿)
- GC会产生停顿(Sun也叫它 "Stop The World"),OoMap 存放着GC Roots,不是每条指令都生成一个。 不是任何时都能停下来进行 GC ,只有在 "特定的位置" 才可以GC 这个位置也叫安全点(Safepoint) 安全点的选定基本上是以程序"是否具有让程序长时间执行的特征"为标准选定的
- 如何在GC发生的时让所有线程都"跑"到最近的安全点上在停下来<br>
有两种方案:<br>
a. 抢先式中断(Preemptive Suspension)(现在几乎都这种方案):不需要线程的执行代码主动配合,GC发生时候先把线程全部中断,如果有线程不在安全点,就回复线程让它跑到安全点。<br>
b. 主动式中断(Voluntary Suspension):当GC需要中断线程的时候,不对线程造作,仅仅简单地设置一个标志位,各个线程执行的时候主动去轮询这个标志位,发现中断标志位真就挂起,轮询标志的地方安全点重合。而对于不执行的线程,任何时间都是安全的也称为安全区;<br>
 
 