## java基础
### 语法

#### 8种基本类型
<table>
  <thead>
    <tr>
      <th colspan="2">类型</th><th>占用空间(字节）</th><th>包装类型</th><th>取值范围</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td rowspan="5">整数</td> <td>byte</td><td>1</td><td>Byte</td><td>-2<sup>7</sup> ~ 2<sup>7</sup>-1，即 -128 ~ 127</td>
    </tr>
    <tr>
      <td>char</td><td>2</td><td>Character</td><td>\u0000~\uFFFF（0~65535）（char c = ’ \u0031 ‘;输出的结果仍然是1）</td>
    </tr>
    <tr>
      <td>short</td><td>2</td><td>Short</td><td>-2<sup>15</sup> ~ 2<sup>15</sup>-1，即-32768～32767</td>
    </tr>
    <tr>
      <td>int</td><td>4</td><td>Integer</td><td>-2<sup>31</sup> ~ 2<sup>31</sup>-1，即-2147483648～2147483647</td>
    </tr>
    <tr>
      <td>long</td><td>8</td><td>Long</td><td>-2<sup>63</sup> ~ 2<sup>63</sup>-1，即-9223372036854774808～9223372036854774807</td>
    </tr>
    <tr>
      <td rowspan="2">浮点数</td><td>float</td><td>4</td><td>Float</td><td>-10<sup>45</sup> ~ 10<sup>38</sup>，（e+38表示是乘以10的38次方，同样，e-45表示乘以10的负45次方）</td>
    </tr>
    <tr>
      <td>double</td><td>8</td><td>Double</td><td>1.797693e+308~ 4.9000000e-324</td>
    </tr>
    <tr>
      <td colspan="2">boolean</td><td>?</td><td>Boolean</td><td>true和false</td>
    </tr>
  </tbody>
</table>
**`大小：`**1MB=1024KB=1024×1024B=1048576B(字节)
2^10 Byte = 1024 Byte = 1KB<br>
2^30 Byte = (2^10)^3 Byte = 1024 * 1024 * 1024 Byte = 1GB

##### Integer变量相等 == 
```java
public  class TestIntegerCache {
    public static void main(String[] args){
        Integer i1 = Integer.valueOf(100);//Integer i3 = 100;
        Integer i2 = Integer.valueOf(100);//Integer i4 = 100;
        System.out.println(i1 == i2); //true
        Integer i3 = Integer.valueOf(1000);//Integer i5 = 1000;
        Integer i4 = Integer.valueOf(1000);//Integer i6 = 1000;
        System.out.println(i3 == i4); //false
        /**
         Integer 在valueOf时缓存了[-128~127]
         public static Integer valueOf(int i) {
            if (i >= IntegerCache.low && i <= IntegerCache.high)
                return IntegerCache.cache[i + (-IntegerCache.low)];
            return new Integer(i);
         }
         * */
    }
}
```
##### String常量相等（==）
```java
 public class TestString {
     public static void main(String[] args) {
         String s3 = "s";
         String s4 = "s";
         System.out.println(s3==s4); //true
 
         String s5 = "RunningCoding";
         String s6 = "Running"+"Coding";
         System.out.println(s5==s6); //true
 
         String s1 = new String("s");
         String s2 = new String("s");
         System.out.println(s1==s2); //false
         System.out.println(s1.intern()==s2.intern()); //true
 
         /***
          * String s = “s” 是常量池中创建一个对象”s”，所以是true。
          * 而String s = new String（”s”）在堆上面分配内存创建一个String对象，栈放了对象引用。
          */
     }
 }
```
<img src='//img.blog.csdn.net/20170615170007540'>

#### 修饰符
- 访问限定修饰符

|修饰符        | 同类  |同包   |同包(不同包子类)|所有类|
| :----       | :--- | :---- |:---   |:---- | 
| `private`   |   √  |       |       |       | 
| `default(friendly)`   |   √  |    √  |       |       |  
| `protected` |   √  |    √  |    √  |       | 
| `public`    |   √  |    √  |    √  |    √  |

- 类修饰符

  - `abstract`  —— 将一个类声明为抽象类，没有实现的方法，需要子类提供方法实现。
  - `final`     —— 将一个类生命为最终（即非继承类），表示他不能被其他类继承。

- 成员变量修饰符

  - `final` —— 最终修饰符，指定此变量的值不能变。
  - `static`（静态修饰符）   —— 指定变量被所有对象共享，即所有实例都可以使用该变量。变量属于这个类。
  - `transient`（过度修饰符）—— 指定该变量是系统保留，暂无特别作用的临时性变量。
  - `volatile`（易失修饰符） —— 指定该变量可以同时被几个线程控制和修改。

- 方法修饰符 

  - `final` —— 指定该方法不能被重载。
  - `static` —— 指定不需要实例化就可以激活的一个方法。
  - `synchronize` —— 同步修饰符，在多个线程中，该修饰符用于在运行前，对他所属的方法加锁，以防止其他线程的访问，运行结束后解锁。
  - `native` —— 本地修饰符。指定此方法的方法体是用其他语言在程序外部编写的。

##### static 详解
   static -  表示“全局”或者“静态”的意思 ，用来修饰成员变量和成员方法，也可以形成静态static代码块。<br>
   只要这个类被加载，Java虚拟机就能根据类名在运行时数据区的方法区内定找到。   
  - `static变量`<br> `在对象之间共享值时`和`方便访问变量时`
  - `静态方法`：<br> 静态方法可以直接通过类名调用，任何的实例也都可以调用。
  - `static代码块`：<br>
    JVM加载类时会执行这些静态的代码块，如果static代码块有多个，JVM将按照它们在类中出现的先后顺序依次执行它们，每个代码块只会被执行一次。<br>
**static 例子：**<br>

```java
public class Car {

    static {
        System.out.println(" Car static");
    }

    public Car() {
        System.out.println(" Car()");
    }
}

public class Bus extends  Car {

    static {
        System.out.println(" Bus static");
    }

    private static  String  carColor = getCarColor();

    static String getCarColor(){
        System.out.println(" Bus getCarColor()");
        return "red";
    }

    public Bus() {
        System.out.println(" Bus()");
    }

    public static void MyStatic(){
        System.out.println(" MyStatic()");
    }

    public static void main(String[] args) {
        Bus.MyStatic();
        new Bus();
        System.out.println("----------");
        System.out.println(" My carColor = "+carColor);
        carColor = "purple";
        Bus bus = new Bus();
        System.out.println(" My carColor = "+bus.carColor);
    }
}
/***
* 输出结果：
 Car static
 Bus static
 Bus getCarColor()
 MyStatic()
 Car()
 Bus()
----------
 My carColor = red
 Car()
 Bus()
 My carColor = purple
* */
```
 _说明：_  
  1. static 静态代码块和静态变量只执行一次。
  2. 谁在前，先执行谁。<br>
     a. super类先执行，再执行子类。<br>
     b. 静态代码块和静态变量,谁先谁执行。
  3. 静态变量共享一份，注意修改。   
  
#### 面向对象的三大特性
`封装`、`继承`、`多态`

**封装**：

1.概念：就是把对象的属性和操作（或服务）结合为一个独立的整体，并尽可能隐藏对象的内部实现细节。

2.好处：

(1)隐藏内部实现细节。

**继承**：

1.概念：继承是从已有的类中派生出新的类，新的类能吸收已有类的数据属性和行为，并能扩展新的能力

2.好处：提高代码的复用，缩短开发周期。

**多态**：

1.概念：多态（Polymorphism）按字面的意思就是“多种状态，即同一个实体同时具有多种形式。一般表现形式是程序在运行的过程中，同一种类型在不同的条件下表现不同的结果。多态也称为动态绑定，一般是在运行时刻才能确定方法的具体执行对象。

2.好处：
1）将接口和实现分开，改善代码的组织结构和可读性，还能创建可拓展的程序。
2）消除类型之间的耦合关系。允许将多个类型视为同一个类型。
3）一个多态方法的调用允许有多种表现形式
#### 多态

1. Java通过方法重写和方法重载实现多态 
2. 方法重写是指子类重写了父类的同名方法 
3. 方法重载是指在同一个类中，方法的名字相同，但是参数列表不同   

#### PECS原则(泛型中 extends 和 super 的区别)
<a href='https://itimetraveler.github.io/2016/12/27/%E3%80%90Java%E3%80%91%E6%B3%9B%E5%9E%8B%E4%B8%AD%20extends%20%E5%92%8C%20super%20%E7%9A%84%E5%8C%BA%E5%88%AB%EF%BC%9F/'>来源：泛型中 extends 和 super 的区别</a>
- <? extends T>：是指 “上界通配符（Upper Bounds Wildcards）”
- <? super T>：是指 “下界通配符（Lower Bounds Wildcards）”

##### 什么是PECS（Producer Extends Consumer Super）原则
  频繁往外读取内容的，适合用上界Extends。
  经常往里插入的，适合用下界Super。
##### 例子
- Plate<？ extends Fruit> 覆盖下图中蓝色的区域.
- Plate<？ super Fruit>覆盖下图中红色的区域
<img src='https://itimetraveler.github.io/gallery/java-genericity/lowerBounds.png'>
<img src='https://itimetraveler.github.io/gallery/java-genericity/upperBounds.png'>

```` java
//Lev 1
class Food{}
//Lev 2
class Fruit extends Food{} 
//Lev 3
class Apple extends Fruit{}
class Banana extends Fruit{} 
//Lev 4
class RedApple extends Apple{}
class GreenApple extends Apple{}

public class Plate<T> {

    private T item;

    public Plate(T t){
        item=t;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
    public static void main(String[] args) {
            /**1. 上界<? extends T>不能往里存，只能往外取*/
            Plate<? extends Fruit> p = new Plate<Apple>(new Apple());
    
            //不能存入任何元素
            p.setItem(new Fruit());    //Error
            p.setItem(new Apple());    //Error
    
            //读取出来的东西只能存放在Fruit或它的基类里。
            Object obj   = p.getItem(); //顶级父类(超类)
            Food  food   = p.getItem(); //父类
            Fruit fruit  = p.getItem(); //上界类
            Apple apple  = p.getItem(); //本类Error
    
    
            /**2. 下界<? super T>不影响往里存，但往外取只能放在Object对象里*/
            Plate<? super Fruit> pSuper = new Plate<Fruit>(new Fruit());
            //存入元素正常
            pSuper.setItem(new Fruit());
            pSuper.setItem(new Apple());
    
            //读取出来的东西只能存放在Object类里。
            Apple appleS  = pSuper.getItem();    //Error
            Fruit fruitS  = pSuper.getItem();    //Error
            Object objS = p.getItem();
    }
}

````
  
#### Java 对象引用

   - `强引用`:类似Object obj = new Object() 这类引用,只要强引用还在,垃圾收集器就永远不会回收被引用的对象;
   - `软引用`:用来描述一些还有用但并未必须的对象。内存溢出异常之前,会把这些对象列入回收范围之内进行二次回收。如果回收后还没有足够的内存这回OOM;
   - `弱引用`:用来描述非必须的对象。若引用关联的对象只能活到下一次垃圾回收之前;
   - `虚引用`:唯一目的对象被回收时收到一个系统通知  
   
_`StrongReference`_：Java 的默认引用实现, 它会尽可能长时间的存活于 JVM 内，当没有任何对象指向它时将会被GC回收

_`SoftReference`_：尽可能长时间保留引用，直到JVM内存不足，适合某些缓存应用

_`WeakReference`_：顾名思义, 是一个弱引用, 当所引用的对象在 JVM 内不再有强引用时, 下一次将被GC回收

_`PhantomReference`_：它是最弱的一种引用关系，也无法通过PhantomReference取得对象的实例。仅用来当该对象被回收时收到一个通知
   
   虽然 WeakReference 与 SoftReference 都有利于提高 GC 和 内存的效率，但是 WeakReference ，一旦失去最后一个强引用，就会被 GC 回收，而 SoftReference 会尽可能长的保留引用直到 JVM 内存不足时才会被回收(虚拟机保证), 这一特性使得 SoftReference 非常适合缓存应用。

#### Java移位运算符

Java中有三种移位运算符

1. `<<` :左移运算符,x << 1,相当于x乘以2(不溢出的情况下),低位补0
2. `>>` :带符号右移,x >> 1,相当于x除以2,正数高位补0,负数高位补1
3. `>>>` :无符号右移,忽略符号位,空位都以0补齐

#### Java语言特性

1. Java致力于检查程序在编译和运行时的错误
2. Java虚拟机实现了跨平台接口
3. 类型检查帮助检查出许多开发早期出现的错误
4. Java自己操纵内存减少了内存出错的可能性
5. Java还实现了真数组，避免了覆盖数据的可能

### Java语法糖

1. Java7的switch用字符串 - hashcode方法 switch用于enum枚举
2. 伪泛型 - List<E>原始类型
3. 自动装箱拆箱 - Integer.valueOf和Integer.intValue
4. foreach遍历 - Iterator迭代器实现
5. 条件编译
6. enum枚举类、内部类
7. 可变参数 - 数组
8. 断言语言
9. try语句中定义和关闭资源


### 形参&实参

1. 形式参数可被视为local variable.形参和局部变量一样都不能离开方法。只有在方法中使用，不会在方法外可见。
2. 形式参数只能用final修饰符，其它任何修饰符都会引起编译器错误。但是用这个修饰符也有一定的限制，就是在方法中不能对参数做任何修改。不过一般情况下，一个方法的形参不用final修饰。只有在特殊情况下，那就是：方法内部类。一个方法内的内部类如果使用了这个方法的参数或者局部变量的话，这个参数或局部变量应该是final。
3. 形参的值在调用时根据调用者更改，实参则用自身的值更改形参的值（指针、引用皆在此列），也就是说真正被传递的是实参。


#### JVM如何加载字节码文件？

虚拟机把描述类的数据从Class文件加载到内存，并对数据进行校验、转换解析和初始化，最终形成可被虚拟机直接使用的Java类型，这就是虚拟机的类加载机制。<br>

Java语言中类的加载、连接和初始化过程都是在程序运行期间完成的，领Java具备高度的灵活性。<br>

_类加载的过程_：`加载、连接（验证、准备、解析）、初始化`。<br>
<a href='https://itimetraveler.github.io/2017/06/02/%E8%AF%A6%E8%A7%A3Java%E7%B1%BB%E7%9A%84%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F/'>详细介绍</a>
`加载`： 通过一个类的名字获取此类的二进制字节流（PS：不限于从文件中读取）；<br>
        将这个字节流代表的静态存储结构转换为方法区的运行时结构（由具体的虚拟机自己定义）；<br>
       在内存中生成一个java.lang.Class对象，作为方法区这个类的各种数据结构的访问入口。<br>
  
`验证`：文件格式验证、元数据验证（语义分析，类与类的继承关系等）、字节码验证（数据流和控制流分析）、符号引用验证（对类自身以外的信息进行匹配校验）<br>
`准备`：正式为类变量分配内存并设置初始值，这里类变量指的是被static修饰的变量。例外：如果类字段是常量，则在这里会被初始化为表达式指定的值。<br>
`解析`：将常量池内的符号引用替换为直接引用。<br>
   - 符号引用：类似于OS中的逻辑地址；<br>
   - 直接引用：类似于OS中的物理地址，直接指向目标的指针、相对偏移量或一个能间接定位到目标的句柄。<br>
   
`初始化`：真正开始执行类中定义的Java程序代码；初始化用于执行Java类的构造方法。类初始化的过程是不可逆的，如果中间一步出错，则无法执行下一步。<br>

#### 面向对象的五大基本原则(solid)

1. S单一职责`SRP`:Single-Responsibility Principle
一个类,最好只做一件事,只有一个引起它的变化。单一职责原则可以看做是低耦合,高内聚在面向对象原则的引申,将职责定义为引起变化的原因,以提高内聚性减少引起变化的原因。

2. O开放封闭原则`OCP`:Open-Closed Principle
软件实体应该是可扩展的,而不是可修改的。对扩展开放,对修改封闭

3. L里氏替换原则`LSP`:Liskov-Substitution Principle
子类必须能够替换其基类。这一思想表现为对继承机制的约束规范,只有子类能够替换其基类时,才能够保证系统在运行期内识别子类,这是保证继承复用的基础。

4. I接口隔离原则`ISP`:Interface-Segregation Principle
使用多个小的接口,而不是一个大的总接口

5. D依赖倒置原则`DIP`:Dependency-Inversion Principle
依赖于抽象。具体而言就是高层模块不依赖于底层模块,二者共同依赖于抽象。抽象不依赖于具体,具体依赖于抽象。

#### 面向对象设计其他原则

1. 封装变化
2. 少用继承 多用组合
3. 针对接口编程 不针对实现编程
4. 为交互对象之间的松耦合设计而努力
5. 类应该对扩展开发 对修改封闭（开闭OCP原则）
6. 依赖抽象，不要依赖于具体类（依赖倒置DIP原则）
7. 密友原则：只和朋友交谈（最少知识原则，迪米特法则）
	
	说明：一个对象应当对其他对象有尽可能少的了解，将方法调用保持在界限内，只调用属于以下范围的方法：
	该对象本身（本地方法）对象的组件 被当作方法参数传进来的对象 此方法创建或实例化的任何对象

8. 别找我（调用我） 我会找你（调用你）（好莱坞原则）
9. 一个类只有一个引起它变化的原因（单一职责SRP原则）

####  null可以被强制转型为任意类型的对象

#### 代码执行次序

1. 多个静态成员变量, 静态代码块按顺序执行
2. 单个类中: 静态代码 -> main方法 -> 构造块 -> 构造方法
3. 构造块在每一次创建对象时执行
4. 涉及父类和子类的初始化过程<br>
	a.初始化父类中的静态成员变量和静态代码块<br>
	b.初始化子类中的静态成员变量和静态代码块<br>
	c.初始化父类的普通成员变量和构造代码块(按次序)，再执行父类的构造方法(注意父类构造方法中的子类方法覆盖)<br>
	d.初始化子类的普通成员变量和构造代码块(按次序)，再执行子类的构造方法


#### String、StringBuffer与StringBuilder的区别

第一点：可变和适用范围。String对象是不可变的，而StringBuffer和StringBuilder是可变字符序列。每次对String的操作相当于生成一个新的String对象，而对StringBuffer和StringBuilder的操作是对对象本身的操作，而不会生成新的对象，所以对于频繁改变内容的字符串避免使用String，因为频繁的生成对象将会对系统性能产生影响。

第二点：线程安全。String由于有final修饰，是immutable的，安全性是简单而纯粹的。StringBuilder和StringBuffer的区别在于StringBuilder不保证同步，也就是说如果需要线程安全需要使用StringBuffer，不需要同步的StringBuilder效率更高。

####  受检查异常和运行时异常**
![](//uploadfiles.nowcoder.com/images/20151010/214250_1444467985224_6A144C1382BBEF1BE30E9B91BC2973C8)

- 粉红色的是受检查的异常(checked exceptions),其必须被try...catch语句块所捕获, 或者在方法签名里通过throws子句声明。受检查的异常必须在编译时被捕捉处理,命名为Checked Exception是因为Java编译器要进行检查, Java虚拟机也要进行检查, 以确保这个规则得到遵守。 

常见的checked exception：ClassNotFoundException IOException FileNotFoundException EOFException

- 绿色的异常是运行时异常(runtime exceptions), 需要程序员自己分析代码决定是否捕获和处理,比如空指针,被0除... 

- 常见的runtime exception：NullPointerException ArithmeticException ClassCastException IllegalArgumentException IllegalStateException IndexOutOfBoundsException NoSuchElementException 

- 而声明为Error的，则属于严重错误，如系统崩溃、虚拟机错误、动态链接失败等，这些错误无法恢复或者不可能捕捉，将导致应用程序中断，Error不需要捕获。 


### 集合
 - <a href='https://runcoding.github.io/?sidebar=develop/back_end/java#/develop/back_end/java/wiki/information/java/java_collection'>`Java集合`</a>
 
### GC
 - <a href='https://runcoding.github.io/?sidebar=develop/back_end/java#/develop/back_end/java/wiki/information/java/java_gc?id=jvm%e5%86%85%e5%ad%98%e6%a8%a1%e5%9e%8b'>`JVM内存模型`</a>
 - <a href='https://runcoding.github.io/?sidebar=develop/back_end/java#/develop/back_end/java/wiki/information/java/java_gc?id=java%e8%bf%90%e8%a1%8c%e6%97%b6%e6%95%b0%e6%8d%ae%e5%8c%ba'>`Java运行时数据区`</a>
 - <a href='https://runcoding.github.io/?sidebar=develop/back_end/java#/develop/back_end/java/wiki/information/java/java_gc?id=%e5%9e%83%e5%9c%be%e6%94%b6%e9%9b%86%e7%ae%97%e6%b3%95'>`垃圾收集算法`</a>
 - <a href='https://runcoding.github.io/?sidebar=develop/back_end/java#/develop/back_end/java/wiki/information/java/java_gc?id=%e8%99%9a%e6%8b%9f%e6%9c%ba%e5%8f%82%e6%95%b0%e8%ae%be%e7%bd%ae'>
 `虚拟机参数设置`
 </a>
 - <a href='https://runcoding.github.io/?sidebar=develop/back_end/java#/develop/back_end/java/wiki/information/java/java_gc?id=gc%e5%ae%89%e5%85%a8%e7%82%b9gc%e4%bc%9a%e4%ba%a7%e7%94%9f%e5%81%9c%e9%a1%bf'>
 `GC安全点(GC会产生停顿)`
 </a>
 
### 设计模式
  - <a href='https://runcoding.github.io/?sidebar=develop/back_end/java#/develop/back_end/java/wiki/information/java/design_pattern'>`Java设计模式`</a>
  
### 线程&并发 

#### 创建线程

-   继承Thread类,并重写了 run() 方法。
-   实现Runnable接口,并重写了 run() 方法。
#### 线程的5种状态
- 新建状态（New）:线程对象被创建后，就进入了新建状态。eg，Thread = new Thread();
- 就绪状态（Runnable）:又称可执行状态。线程对象创建后调用了start() 方法启动线程
- 运行状态（Running）:线程获取CPU进行执行。只能从就绪状态进入运行状态。
- 阻塞状态（Blocked）:因某种原因放弃CPU使用权，暂时停止运行。原因有三种：<br>
等待阻塞 --> 通过调用线程的wait()或sleep()或join()方法，让线程等待某工作完成。<br>
同步阻塞 --> 线程在获取synchronized同步锁失败（被其他线程占用）<br>
其他阻塞 --> 通过调用线程的发出了I/O请求时，线程会进入同步阻塞状态<br>
- 死亡状态（Dead）:线程执行完了或异常退出了run()方法。结束生命周期<br>
<img src="https://runcoding.github.io/static/wiki/learn-java/java/thread_run.png" >

#### 同步操作
- CountDownLatch 通常用来使主线程等待其他线程执行完再执行所用到
- ReentrantLock 并发时，它增加了一些synchronized没有的方法，更方便管理
- Synchronized 简单的同步就用这个吧

#### interrupt相关
- 当某个线程调用了interrupt()方法后，相当于给该线程打上了一个中断标志，如果线程正好处于阻塞状态，会直接抛出InterruptedException 异常
- interrupted()方法用来检测“当前线程”的中断状态，且会将中断状态标志清除。
- isInterrupted()方法用来检测“this”的中断状态，且不会改变线程的状态标志。<br>

 获取线程终端状态 要用Thread.currentThread().isInterrupted() 比较标准
### 算法
- <a href='/?sidebar=java/java#/learn-java/wiki/information/java/java_algorithm'>
**`算法整理`**
</a>
-  <a href='https://itimetraveler.github.io/2017/07/18/%E5%85%AB%E5%A4%A7%E6%8E%92%E5%BA%8F%E7%AE%97%E6%B3%95%E6%80%BB%E7%BB%93%E4%B8%8Ejava%E5%AE%9E%E7%8E%B0/'>
**`八大排序算法总结与java实现`**
</a>
