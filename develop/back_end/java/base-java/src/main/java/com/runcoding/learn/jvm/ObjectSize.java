package com.runcoding.learn.jvm;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * @author xk
 * https://www.jianshu.com/p/5790d82e8e73
 * 方式1. 去掉package com.runcoding.learn.jvm
 * javac ObjectSize.java
 * java -jar ~/Downloads/jol-cli-0.9-full.jar internals -cp . ObjectSize
 * 方式2. 打包
 * java -jar ~/Downloads/jol-cli-0.9-full.jar internals -cp ~/projects/soft_develop/github/runcoding.github.io/develop/back_end/java/base-java/target/base-java-1.0.0-SNAPSHOT.jar com.runcoding.learn.jvm.ObjectSize
 *
 * $ java -jar ~/Downloads/jol-cli-0.9-full.jar internals -cp . ObjectSize
 * # WARNING: Unable to attach Serviceability Agent. You can try again with escalated privileges. Two options: a) use -Djol.tryWithSudo=true to try with sudo; b) echo 0 | sudo tee /proc/sys/kernel/yama/ptrace_scope
 * # Running 64-bit HotSpot VM.
 * # Using compressed oop with 3-bit shift.
 * # Using compressed klass with 3-bit shift.
 * # WARNING | Compressed references base/shifts are guessed by the experiment!
 * # WARNING | Therefore, computed addresses are just guesses, and ARE NOT RELIABLE.
 * # WARNING | Make sure to attach Serviceability Agent to get the reliable addresses.
 * # Objects are 8 bytes aligned.
 * # Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
 * # Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
 *
 * Instantiated the sample instance via default constructor.
 *
 * ObjectSize object internals:
 *  OFFSET  SIZE             TYPE DESCRIPTION                               VALUE
 *       0     4                  (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 *       4     4                  (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4                  (object header)                           05 08 02 f8 (00000101 00001000 00000010 11111000) (-134084603)
 *      12     4   java.util.List ObjectSize.list                           (object)
 *      16     8             long ObjectSize.value                          0
 *      24     8             long ObjectSize.p1                             0
 *      32     8             long ObjectSize.p2                             0
 *      40     8             long ObjectSize.p3                             0
 *      48     8             long ObjectSize.p4                             0
 *      56     8             long ObjectSize.p5                             0
 *      64     8             long ObjectSize.p6                             0
 *      72     4    java.util.Map ObjectSize.map                            (object)
 *      76     4                  (loss due to the next object alignment)
 * Instance size: 80 bytes
 * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 */
public class ObjectSize {

    public volatile long value = 0L;

    public List<String> list  = new ArrayList<>(16);

    public long p1, p2, p3, p4, p5, p6;

    public Map map = new HashMap(50);

    public static void main(String[] args) {

    }

}
