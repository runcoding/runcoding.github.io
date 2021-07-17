
#### 集合框架体系如图：
<img src='https://runcoding.github.io/static/wiki/learn-java/java/java-coll.png'>
```
Collection - List - ArrayList
Collection - List - LinkedList
Collection - List - Vector
Collection - List - Vector - Stack
Collection - Set - HashSet
Collection - Set - TreeSet
Collection - List - LinkedHashSet
Map - HashMap
Map - TreeMap
Map - HashTable
Map - LinkedHashMap
Map - ConcurrentHashMap
```
集合实现类（集合类）

  <table> 
   <tbody> 
    <tr> 
     <th width="10%">序号</th> 
     <th>类描述</th> 
    </tr> 
    <tr> 
     <td>1</td> 
     <td><strong>AbstractCollection&nbsp;</strong><br /> 实现了大部分的集合接口。</td> 
    </tr> 
    <tr> 
     <td>2</td> 
     <td><strong>AbstractList&nbsp;</strong><br /> 继承于AbstractCollection 并且实现了大部分List接口。</td> 
    </tr> 
    <tr> 
     <td>3</td> 
     <td><strong>AbstractSequentialList&nbsp;</strong><br /> 继承于 AbstractList ，提供了对数据元素的链式访问而不是随机访问。</td> 
    </tr> 
    <tr> 
     <td>4</td> 
     <td>LinkedList<br /> <p> 该类实现了List接口，允许有null（空）元素。主要用于创建链表数据结构，该类没有同步方法，如果多个线程同时访问一个List，则必须自己实现访问同步，解决方法就是在创建List时候构造一个同步的List。例如： </p> <pre class="prettyprint prettyprinted" style=""><span class="typ">Listlist</span><span class="pun">=</span><span class="typ">Collections</span><span class="pun">.</span><span class="pln">synchronizedList</span><span class="pun">(</span><span class="pln">newLinkedList</span><span class="pun">(...));</span></pre> <p>LinkedList 查找效率低。</p> </td> 
    </tr> 
    <tr> 
     <td>5</td> 
     <td>ArrayList<br /> <p> 该类也是实现了List的接口，实现了可变大小的数组，随机访问和遍历元素时，提供更好的性能。该类也是非同步的,在多线程的情况下不要使用。ArrayList 增长当前长度的50%，插入删除效率低。 </p></td> 
    </tr> 
    <tr> 
     <td>6</td> 
     <td><strong>AbstractSet&nbsp;</strong><br /> 继承于AbstractCollection 并且实现了大部分Set接口。</td> 
    </tr> 
    <tr> 
     <td>7</td> 
     <td>HashSet<br /><p> 该类实现了Set接口，不允许出现重复元素，不保证集合中元素的顺序，允许包含值为null的元素，但最多只能一个。</p></td> 
    </tr> 
    <tr> 
     <td>8</td> 
     <td>LinkedHashSet<br /> 具有可预知迭代顺序的&nbsp;<tt>Set</tt>&nbsp;接口的哈希表和链接列表实现。</td> 
    </tr> 
    <tr> 
     <td>9</td> 
     <td>TreeSet<br /> <p>该类实现了Set接口，可以实现排序等功能。</p></td> 
    </tr> 
    <tr> 
     <td>10</td> 
     <td><strong>AbstractMap&nbsp;</strong><br /> 实现了大部分的Map接口。</td> 
    </tr> 
    <tr> 
     <td>11</td> 
     <td>HashMap <br /> HashMap 是一个散列表，它存储的内容是键值对(key-value)映射。<br /> 该类实现了Map接口，根据键的HashCode值存储数据，具有很快的访问速度，最多允许一条记录的键为null，不支持线程同步。 </td> 
    </tr> 
    <tr> 
     <td>12</td> 
     <td>TreeMap <br /> 继承了AbstractMap，并且使用一颗树。</td> 
    </tr> 
    <tr> 
     <td>13</td> 
     <td>WeakHashMap <br /> 继承AbstractMap类，使用弱密钥的哈希表。</td> 
    </tr> 
    <tr> 
     <td>14</td> 
     <td>LinkedHashMap <br /> 继承于HashMap，使用元素的自然顺序对元素进行排序.</td> 
    </tr> 
    <tr> 
     <td>15</td> 
     <td>IdentityHashMap <br /> 继承AbstractMap类，比较文档时使用引用相等。</td> 
    </tr> 
   </tbody> 
  </table>

#### HashMap和Hashtable的区别

-  Hashtable是基于陈旧的Dictionary的Map接口的实现，而HashMap是基于哈希表的Map接口的实现
-  从方法上看，HashMap去掉了Hashtable的contains方法
-  HashTable是同步的(线程安全)，而HashMap线程不安全，效率上HashMap更快
-  HashMap允许空键值，而Hashtable不允许
-  HashMap的iterator迭代器执行快速失败机制，也就是说在迭代过程中修改集合结构，除非调用迭代器自身的remove方法，否则以其他任何方式的修改都将抛出并发修改异常。而Hashtable返回的Enumeration不是快速失败的。

注：`Fast-fail`机制:在使用迭代器的过程中有其它线程修改了集合对象结构或元素数量,都将抛出ConcurrentModifiedException，但是抛出这个异常是不保证的，我们不能编写依赖于此异常的程序。<br>
 <a href='//www.importnew.com/22011.html'>为什么HashMap是线程不安全的</a>?
 答：HashMap在扩容时需要resize，而扩容时需要重新计算rehash，需要transfer(遍历所有元素，转移元素)

#### List集合和Set集合

List中元素存取是有序的、可重复的；Set集合中元素是无序的，不可重复的。

List和数组类似，可以动态增长，根据实际存储的数据的长度自动增长List的长度。
查找元素效率高，插入删除效率低，因为会引起其他元素位置改变 <实现类有ArrayList,LinkedList,Vector> 。

Set检索效率低下，删除和插入效率高，插入和删除不会引起元素位置改变 <实现类有HashSet,TreeSet>

CopyOnWriteArrayList:COW的策略，即写时复制的策略。适用于读多写少的并发场景

HashSet不保证迭代顺序，线程不安全；LinkedHashSet是Set接口的哈希表和链接列表的实现，保证迭代顺序，线程不安全。

TreeSet：可以对Set集合中的元素排序，元素以二叉树形式存放，线程不安全。

#### ArrayList、LinkedList、Vector的区别

首先它们均是List接口的实现。

ArrayList、LinkedList的区别
ArrayList:<br>
- 无参构造 容量为10
- ArrayList(Collections<?extends E> c)构造包含指定collection的元素的列表
- ArrayList(int initialCapacity) 指定初始容量<br>

1.随机存取：ArrayList是基于可变大小的数组实现，LinkedList是链接列表的实现。这也就决定了对于随机访问的get和set的操作，ArrayList要优于LinkedList，因为LinkedList要移动指针。

2.插入和删除：LinkedList要好一些，因为ArrayList要移动数据，更新索引。

内存消耗：LinkedList需要更多的内存，因为需要维护指向后继结点的指针。

Vector从JDK 1.0起就存在，在1.2时改为实现List接口，功能与ArrayList类似，但是Vector具备线程安全。

#### Map集合

Hashtable:基于Dictionary类，线程安全，速度快。底层是哈希表数据结构。是同步的。
不允许null作为键，null作为值。

Properties:Hashtable的子类。用于配置文件的定义和操作，使用频率非常高，同时键和值都是字符串。

HashMap：线程不安全，底层是数组加链表实现的哈希表。允许null作为键，null作为值。HashMap去掉了contains方法。
注意：HashMap不保证元素的迭代顺序。如果需要元素存取有序，请使用LinkedHashMap

TreeMap：可以用来对Map集合中的键进行排序。

ConcurrentHashMap:是JUC包下的一个并发集合。

####  为什么使用ConcurrentHashMap而不是HashMap或Hashtable？

HashMap的缺点：主要是多线程同时put时，如果同时触发了rehash操作，会导致HashMap中的链表中出现循环节点，进而使得后面get的时候，会死循环，CPU达到100%，所以在并发情况下不能使用HashMap。让HashMap同步：Map m = Collections.synchronizeMap(hashMap);而Hashtable虽然是同步的，使用synchronized来保证线程安全，但在线程竞争激烈的情况下HashTable的效率非常低下。因为当一个线程访问HashTable的同步方法时，其他线程访问HashTable的同步方法时，可能会进入阻塞或轮询状态。如线程1使用put进行添加元素，线程2不但不能使用put方法添加元素，并且也不能使用get方法来获取元素，所以竞争越激烈效率越低。 

ConcurrentHashMap的原理：

HashTable容器在竞争激烈的并发环境下表现出效率低下的原因在于所有访问HashTable的线程都必须竞争同一把锁，那假如容器里有多把锁，每一把锁用于锁容器其中一部分数据，那么当多线程访问容器里不同数据段的数据时，线程间就不会存在锁竞争，从而可以有效的提高并发访问效率，这就是ConcurrentHashMap所使用的锁分段技术，首先将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。

ConcurrentHashMap的结构：

ConcurrentHashMap是由Segment数组结构和HashEntry数组结构组成。Segment是一种可重入互斥锁ReentrantLock，在ConcurrentHashMap里扮演锁的角色，HashEntry则用于存储键值对数据。一个ConcurrentHashMap里包含一个Segment数组，Segment的结构和HashMap类似，是一种数组和链表结构， 一个Segment里包含一个HashEntry数组，每个HashEntry是一个链表结构的元素，当对某个HashEntry数组的数据进行修改时，必须首先获得它对应的Segment锁。

ConcurrentHashMap的构造、get、put操作：

构造函数：传入参数分别为 
- 1、初始容量，默认16
- 2、装载因子 装载因子用于rehash的判定，就是当ConcurrentHashMap中的元素大于装载因子*最大容量时进行扩容，默认0.75 
- 3、并发级别 这个值用来确定Segment的个数，Segment的个数是大于等于concurrencyLevel的第一个2的n次方的数。比如，如果concurrencyLevel为12，13，14，15，16这些数，则Segment的数目为16(2的4次方)。默认值为static final int DEFAULT_CONCURRENCY_LEVEL = 16;。理想情况下ConcurrentHashMap的真正的并发访问量能够达到concurrencyLevel，因为有concurrencyLevel个Segment，假如有concurrencyLevel个线程需要访问Map，并且需要访问的数据都恰好分别落在不同的Segment中，则这些线程能够无竞争地自由访问（因为他们不需要竞争同一把锁），达到同时访问的效果。这也是为什么这个参数起名为“并发级别”的原因。默认16.

初始化的一些动作：

初始化segments数组（根据并发级别得到数组大小size），默认16

初始化segmentShift和segmentMask（这两个全局变量在定位segment时的哈希算法里需要使用），默认情况下segmentShift为28，segmentMask为15

初始化每个Segment，这一步会确定Segment里HashEntry数组的长度.

put操作：

1、判断value是否为null，如果为null，直接抛出异常。

2、key通过一次hash运算得到一个hash值。将得到hash值向右按位移动segmentShift位，然后再与segmentMask做&运算得到segment的索引j。即segmentFor方法

3、使用Unsafe的方式从Segment数组中获取该索引对应的Segment对象。向这个Segment对象中put值，这个put操作也基本是一样的步骤（通过&运算获取HashEntry的索引，然后set）。

get操作：

1、和put操作一样，先通过key进行hash确定应该去哪个Segment中取数据。

2、使用Unsafe获取对应的Segment，然后再进行一次&运算得到HashEntry链表的位置，然后从链表头开始遍历整个链表（因为Hash可能会有碰撞，所以用一个链表保存），如果找到对应的key，则返回对应的value值，如果链表遍历完都没有找到对应的key，则说明Map中不包含该key，返回null。

定位Segment的hash算法：(hash >>> segmentShift) & segmentMask

定位HashEntry所使用的hash算法：int index = hash & (tab.length - 1);

注：

1\.tab为HashEntry数组

2\.ConcurrentHashMap既不允许null key也不允许null value

####  Collection 和 Collections的区别

Collection是集合类的上级接口，子接口主要有Set 和List、Queue
Collections是针对集合类的一个辅助类，提供了操作集合的工具方法：一系列静态方法实现对各种集合的搜索、排序、线程安全化等操作。

####  Map、Set、List、Queue、Stack的特点与用法

- Set集合类似于一个罐子，"丢进"Set集合里的多个对象之间没有明显的顺序。
- List集合代表元素有序、可重复的集合，集合中每个元素都有其对应的顺序索引。
- Stack是Vector提供的一个子类，用于模拟"栈"这种数据结构(LIFO后进先出)。
- Queue用于模拟"队列"这种数据结构(先进先出 FIFO)。 Map用于保存具有"映射关系"的数据，因此Map集合里保存着两组值。

#### 7HashMap的工作原理

HashMap维护了一个Entry数组，Entry内部类有key,value，hash和next四个字段，其中next也是一个Entry类型。可以将Entry数组理解为一个个的散列桶。每一个桶实际上是一个单链表。当执行put操作时，会根据key的hashcode定位到相应的桶。遍历单链表检查该key是否已经存在，如果存在，覆盖该value，反之，新建一个新的Entry，并放在单链表的头部。当通过传递key调用get方法时，它再次使用key.hashCode()来找到相应的散列桶，然后使用key.equals()方法找出单链表中正确的Entry，然后返回它的值。

####  Map的实现类的介绍

HashMap基于散列表来的实现，即使用hashCode()进行快速查询元素的位置，显著提高性能。插入和查询“键值对”的开销是固定的。可以通过设置容量和装载因子，以调整容器的性能。

LinkedHashMap, 类似于HashMap,但是迭代遍历它时，保证迭代的顺序是其插入的次序，因为它使用链表维护内部次序。此外可以在构造器中设定LinkedHashMap，使之采用LRU算法。使没有被访问过的元素或较少访问的元素出现在前面，访问过的或访问多的出现在后面。这对于需要定期清理元素以节省空间的程序员来说，此功能使得程序员很容易得以实现。

TreeMap, 是基于红黑树的实现。同时TreeMap实现了SortedMap接口，该接口可以确保键处于排序状态。所以查看“键”和“键值对”时，所有得到的结果都是经过排序的，次序由自然排序或提供的Comparator决定。SortedMap接口拥有其他额外的功能，如：返回当前Map使用的Comparator比较强，firstKey()，lastKey(),headMap(toKey),tailMap(fromKey)以及可以返回一个子树的subMap()方法等。

WeakHashMap，表示弱键映射，WeakHashMap 的工作与正常的 HashMap 类似，但是使用弱引用作为 key，意思就是当 key 对象没有任何引用时，key/value 将会被回收。

ConcurrentHashMap， 在HashMap基础上分段锁机制实现的线程安全的HashMap。

IdentityHashMap 使用==代替equals() 对“键”进行比较的散列映射。专为解决特殊问题而设计。

HashTable：基于Dictionary类的Map接口的实现，它是线程安全的。

#### LinkedList 和 PriorityQueue 的区别

它们均是Queue接口的实现。拥有FIFO的特点，它们的区别在于排序行为。LinkedList 支持双向列表操作，
PriorityQueue 按优先级组织的队列，元素的出队次序由元素的自然排序或者由Comparator比较器指定。
 

#### BlockingQueue

Java.util.concurrent.BlockingQueue是一个队列，在进行获取元素时，它会等待队列变为非空；当在添加一个元素时，它会等待队列中的可用空间。BlockingQueue接口是Java集合框架的一部分，主要用于实现生产者-消费者模式。我们不需要担心等待生产者有可用的空间，或消费者有可用的对象，因为它都在BlockingQueue的实现类中被处理了。Java提供了集中BlockingQueue的实现，比如ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue,、SynchronousQueue等。

#### 如何对一组对象进行排序

如果需要对一个对象数组进行排序，我们可以使用Arrays.sort()方法。如果我们需要排序一个对象列表，我们可以使用Collections.sort()方法。排序时是默认根据元素的自然排序（使用Comparable）或使用Comparator外部比较器。Collections内部使用数组排序方法，所有它们两者都有相同的性能，只是Collections需要花时间将列表转换为数组。


####   List和Set

List 是一个有序集合，允许元素重复。它的某些实现可以提供基于下标值的常量访问时间，但是这不是 List 接口保证的。Set 是一个无序集合。

####   poll() 方法和 remove() 方法的区别？

poll() 和 remove() 都是从队列中取出一个元素，但是 poll() 在获取元素失败的时候会返回空，但是 remove() 失败的时候会抛出异常。

####   Java 中 LinkedHashMap 和 PriorityQueue 的区别是什么？

PriorityQueue 保证最高或者最低优先级的的元素总是在队列头部，但是 LinkedHashMap 维持的顺序是元素插入的顺序。当遍历一个 PriorityQueue 时，没有任何顺序保证，但是 LinkedHashMap 课保证遍历顺序是元素插入的顺序。

####   ArrayList 与 LinkedList 的区别？

最明显的区别是 ArrrayList 底层的数据结构是数组，支持随机访问，而 LinkedList 的底层数据结构书链表，不支持随机访问。使用下标访问一个元素，ArrayList 的时间复杂度是 O(1)，而 LinkedList 是 O(n)。

####   用哪两种方式来实现集合的排序？

你可以使用有序集合，如 TreeSet 或 TreeMap，你也可以使用有顺序的的集合，如 list，然后通过 Collections.sort() 来排序。

####   Java 中怎么打印数组？

你可以使用 Arrays.toString() 和 Arrays.deepToString() 方法来打印数组。由于数组没有实现 toString() 方法，所以如果将数组传递给 System.out.println() 方法，将无法打印出数组的内容，但是 Arrays.toString() 可以打印每个元素。

####   Java 中的 LinkedList 是单向链表还是双向链表？

是双向链表，你可以检查 JDK 的源码。在 Eclipse，你可以使用快捷键 Ctrl + T，直接在编辑器中打开该类。

####   Java 中的 TreeMap 是采用什么树实现的？

Java 中的 TreeMap 是使用红黑树实现的。

####   Java 中的 HashSet，内部是如何工作的？

HashSet 的内部采用 HashMap来实现。由于 Map 需要 key 和 value，所以所有 key 的都有一个默认 value。类似于 HashMap，HashSet 不允许重复的 key，只允许有一个null key，意思就是 HashSet 中只允许存储一个 null 对象。

####   写一段代码在遍历 ArrayList 时移除一个元素？

该问题的关键在于面试者使用的是 ArrayList 的 remove() 还是 Iterator 的 remove()方法。这有一段示例代码，是使用正确的方式来实现在遍历的过程中移除元素，而不会出现 ConcurrentModificationException 异常的示例代码。

####   我们能自己写一个容器类，然后使用 for-each 循环吗？

可以，你可以写一个自己的容器类。如果你想使用 Java 中增强的循环来遍历，你只需要实现 Iterable 接口。如果你实现 Collection 接口，默认就具有该属性。

####   ArrayList 和 HashMap 的默认大小是多数？

在 Java 7 中，ArrayList 的默认大小是 10 个元素，HashMap 的默认大小是16个元素（必须是2的幂）。这就是 Java 7 中 ArrayList 和 HashMap 类的代码片段：

```Java
// from ArrayList.Java JDK 1.7
private static final int DEFAULT_CAPACITY = 10;
 
//from HashMap.Java JDK 7
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
```

####  数组复制方法

1. for逐一复制
2. System.arraycopy() -> 效率最高native方法
3. Arrays.copyOf() -> 本质调用arraycopy
4. clone方法 -> 返回Object[],需要强制类型转换