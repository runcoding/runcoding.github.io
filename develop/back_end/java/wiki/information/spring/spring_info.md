 ## **Spring 事务管理分为编码式和声明式的两种方式**
@参考来源：https://www.ibm.com/developerworks/cn/java/j-master-spring-transactional-use/index.html
- **事务**：一组业务操作，要么全部成功，要么全部失败

### **特性：ACID**

A：`原子性`，是说事务是一个整体，要么全部成功，要么全部失败<br>
C：`一致性`，数据完整，你转100给我，你减100，我要增加100<br>
I：`隔离性`，并发(多个事务)<br>
D：`持久性`，已经提交的事务，就已经保存到数据库中，不能在改变了<br>

### **隔离问题**
如果不对数据库进行并发控制，可能会产生异常情况：
#### 脏读(Dirty Read)
   当一个事务读取另一个事务尚未提交的修改时，产生脏读。 <br>
   同一事务内不是脏读。 一个事务开始读取了某行数据，但是另外一个事务已经更新了此数据但没有能够及时提交。<br>
   这是相当危险的，因为很可能所有的操作都被回滚，也就是说读取出的数据其实是错误的。<br>
#### 不可重复读(Nonrepeatable Read)
 一个事务对同一行数据重复读取两次，但是却得到了不同的结果。同一查询在同一事务中多次进行，由于其他提交事务所做的修改或删除，每次返回不同的结果集，此时发生非重复读。
#### 虚度(幻读)(Phantom Reads)
 事务在操作过程中进行两次查询，第二次查询的结果包含了第一次查询中未出现的数据（这里并不要求两次查询的SQL语句相同）。<br>
 这是因为在两次查询过程中有另外一个事务插入数据造成的。<br>
 当对某行执行插入或删除操作，而该行属于某个事务正在读取的行的范围时，会发生幻像读问题。<br>
#### 丢失修改(Lost Update) 
- `第一类：`当两个事务更新相同的数据源，如果第一个事务被提交，第二个却被撤销，那么连同第一个事务做的更新也被撤销。
- `第二类：`有两个并发事务同时读取同一行数据，然后其中一个对它进行修改提交，而另一个也进行了修改提交。这就会造成第一次写操作失效。

### **解决隔离问题，定义了4个事务隔离级别**

#### 未提交读(Read Uncommitted)

直译就是"读未提交"，意思就是即使一个更新语句没有提交，但是别的事务可以读到这个改变。

Read Uncommitted允许脏读。

#### 已提交读(Read Committed)

直译就是"读提交"，意思就是语句提交以后，即执行了 Commit 以后别的事务就能读到这个改变，只能读取到已经提交的数据。Oracle等多数数据库默认都是该级别。

Read Commited 不允许脏读，但会出现非重复读。

#### 可重复读(Repeatable Read)：

直译就是"可以重复读"，这是说在同一个事务里面先后执行同一个查询语句的时候，得到的结果是一样的。

Repeatable Read 不允许脏读，不允许非重复读，但是会出现幻象读。

#### 串行读(Serializable)

直译就是"序列化"，意思是说这个事务执行的时候不允许别的事务并发执行。完全串行化的读，每次读都需要获得表级共享锁，读写相互都会阻塞。

Serializable 不允许不一致现象的出现。

### 事务隔离的实现——锁

#### 共享锁(S锁)

用于只读操作(SELECT)，锁定共享的资源。共享锁不会阻止其他用户读，但是阻止其他的用户写和修改。

#### 更新锁(U锁)

用于可更新的资源中。防止当多个会话在读取、锁定以及随后可能进行的资源更新时发生常见形式的死锁。

#### 独占锁(X锁，也叫排他锁)

一次只能有一个独占锁用在一个资源上，并且阻止其他所有的锁包括共享缩。写是独占锁，可以有效的防止“脏读”。

- _Read Uncommited_ 如果一个事务已经开始写数据，则另外一个数据则不允许同时进行写操作，但允许其他事务读此行数据。该隔离级别可以通过“排他写锁”实现。

- _Read Committed_ 读取数据的事务允许其他事务继续访问该行数据，但是未提交的写事务将会禁止其他事务访问该行。可以通过“瞬间共享读锁”和“排他写锁”实现。

- _Repeatable Read_ 读取数据的事务将会禁止写事务（但允许读事务），写事务则禁止任何其他事务。可以通过“共享读锁”和“排他写锁”实现。

- _Serializable_ 读加共享锁，写加排他锁，读写互斥。

### 编程式事务指的是通过编码方式实现事务
- 声明式事务基于 AOP,将具体业务逻辑与事务处理解耦,声明式事务管理使业务代码逻辑不受污染, 因此在实际使用中声明式事务用的比较多。
- 声明式事务有两种方式，一种是在配置文件（xml）中做相关的事务规则声明，另一种是基于@Transactional 注解的方式。
- 注释配置是目前流行的使用方式，因此本文将着重介绍基于@Transactional 注解的事务管理。
   
### _@Transactional 注解管理事务的实现步骤_
- 使用@Transactional 注解管理事务的实现步骤分为两步。
#### 第一步
   在xml配置文件中添加如"清单1"的事务配置信息。<br>
   除了用配置文件的方式，@EnableTransactionManagement 注解也可以启用事务管理功能。<br>
   这里以简单的 DataSourceTransactionManager 为例。<br>
`清单 1`. 在 xml 配置中的事务配置信息
```xml
<tx:annotation-driven />
<bean id="transactionManager"
class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<property name="dataSource" ref="dataSource" />
</bean>
```
#### 第二步
 将@Transactional 注解添加到合适的方法上，并设置合适的属性信息。@Transactional 注解的属性信息如表 1 展示。
 
_`表 1`. @Transactional 注解的属性信息_

|属性名|说明|
| :----------------- | :----------------- |
|name           | 当在配置文件中有多个 TransactionManager , 可以用该属性指定选择哪个事务管理器。
|propagation	| 事务的传播行为，默认值为 REQUIRED。
|isolation	    | 事务的隔离度，默认值采用 DEFAULT。
|timeout	    | 事务的超时时间，默认值为-1。如果超过该时间限制但事务还没有完成，则自动回滚事务。
|read-only	    | 指定事务是否为只读事务，默认值为 false；为了忽略那些不需要事务的方法，比如读取数据，可以设置 read-only 为 true。
|rollback-for	| 用于指定能够触发事务回滚的异常类型，如果有多个异常类型需要指定，各类型之间可以通过逗号分隔。
|no-rollback- for|	抛出 no-rollback-for 指定的异常类型，不回滚事务。

除此以外，@Transactional 注解也可以添加到类级别上。
- 当把@Transactional 注解放在类级别时，表示所有该类的公共方法都配置相同的事务属性信息。
- 见清单 2，EmployeeService 的所有方法都支持事务并且是只读。
- 当类级别配置了@Transactional，方法级别也配置了@Transactional，应用程序会以方法级别的事务属性信息来管理事务，换言之，方法级别的事务属性信息会覆盖类级别的相关配置信息。

`清单 2`. @Transactional 注解的类级别支持
```java 
@Transactional(propagation= Propagation.SUPPORTS,readOnly=true)
@Service(value ="employeeService")
public class EmployeeService
```
`注意:`如果对 Spring 中的 @transaction 注解的事务管理理解的不够透彻，就很容易出现错误，比如事务应该回滚（rollback）而没有回滚事务的问题。<br>
      接下来，将首先分析 Spring 的注解方式的事务实现机制，然后列出相关的注意事项，以最终达到帮助开发人员准确而熟练的使用 Spring 的事务的目的。
      
### Spring 的注解方式的事务实现机制
在应用系统调用声明@Transactional 的目标方法时，Spring Framework 默认使用 AOP 代理，在代码运行时生成一个代理对象，<br>
根据@Transactional 的属性配置信息，这个代理对象决定该声明@Transactional 的目标方法是否由拦截器 TransactionInterceptor 来使用拦截，<br>
在 TransactionInterceptor 拦截时，会在在目标方法开始执行之前创建并加入事务，并执行目标方法的逻辑, 最后根据执行情况是否出现异常，<br>
利用抽象事务管理器(图 2 有相关介绍)AbstractPlatformTransactionManager 操作数据源 DataSource 提交或回滚事务, 如图 1 所示。<br>

`图 1.` Spring 事务实现机制
<img src="https://www.ibm.com/developerworks/cn/java/j-master-spring-transactional-use/image001.jpg">

Spring AOP 代理有 CglibAopProxy 和 JdkDynamicAopProxy 两种，图 1 是以 CglibAopProxy 为例，对于 CglibAopProxy，需要调用其内部类的 DynamicAdvisedInterceptor 的 intercept 方法。对于 JdkDynamicAopProxy，需要调用其 invoke 方法。
正如上文提到的，事务管理的框架是由抽象事务管理器 AbstractPlatformTransactionManager 来提供的，而具体的底层事务处理实现，<br>
由 PlatformTransactionManager 的具体实现类来实现，如事务管理器 DataSourceTransactionManager。<br>
不同的事务管理器管理不同的数据资源 DataSource，比如 DataSourceTransactionManager 管理 JDBC 的 Connection。<br>
PlatformTransactionManager，AbstractPlatformTransactionManager 及具体实现类关系如图 2 所示。

`图 2. `TransactionManager 类结构
<img src="https://www.ibm.com/developerworks/cn/java/j-master-spring-transactional-use/image002.jpg">
### **注解方式的事务使用注意事项**
当您对 Spring 的基于注解方式的实现步骤和事务内在实现机制有较好的理解之后，就会更好的使用注解方式的事务管理，避免当系统抛出异常，数据不能回滚的问题。<br>
#### **正确的设置@Transactional 的 propagation 属性**
需要注意下面三种 propagation 可以不启动事务。本来期望目标方法进行事务管理，但若是错误的配置这三种 propagation，事务将不会发生回滚。
- TransactionDefinition.PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
- TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
- TransactionDefinition.PROPAGATION_NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。
正确的设置@Transactional 的 rollbackFor 属性
默认情况下，如果在事务中抛出了未检查异常（继承自 RuntimeException 的异常）或者 Error，则 Spring 将回滚事务；除此之外，Spring 不会回滚事务。<br>
如果在事务中抛出其他类型的异常，并期望 Spring 能够回滚事务，可以指定 rollbackFor。<br>
例：@Transactional(propagation= Propagation.REQUIRED,rollbackFor= MyException.class)<br>
通过分析 Spring 源码可以知道，若在目标方法中抛出的异常是 rollbackFor 指定的异常的子类，事务同样会回滚。<br>
清单 3. RollbackRuleAttribute 的 getDepth 方法
```java 
private int getDepth(Class<?> exceptionClass, int depth) {
        if (exceptionClass.getName().contains(this.exceptionName)) {
            // Found it!
            return depth;
}
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass == Throwable.class) {
            return -1;
}
return getDepth(exceptionClass.getSuperclass(), depth + 1);
}
```
@Transactional 只能应用到 public 方法才有效
只有@Transactional 注解应用到 public 方法，才能进行事务管理。<br>
这是因为在使用 Spring AOP 代理时，Spring 在调用在图 1 中的 TransactionInterceptor 在目标方法执行前后进行拦截之前，<br>
DynamicAdvisedInterceptor（CglibAopProxy 的内部类）的的 intercept 方法或 JdkDynamicAopProxy 的 invoke 方法会间接调用 <br>
AbstractFallbackTransactionAttributeSource（Spring 通过这个类获取表 1. @Transactional 注解的事务属性配置属性信息）的 computeTransactionAttribute 方法。

`清单 4.` AbstractFallbackTransactionAttributeSource
```java 
protected TransactionAttribute computeTransactionAttribute(Method method,Class<?> targetClass) {
        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return null;
         }
}
```
这个方法会检查目标方法的修饰符是不是 public，若不是 public，就不会获取@Transactional 的属性配置信息，<br>
最终会造成不会用 TransactionInterceptor 来拦截该目标方法进行事务管理。
避免 Spring 的 AOP 的自调用问题
在 Spring 的 AOP 代理下，只有目标方法由外部调用，目标方法才由 Spring 生成的代理对象来管理，这会造成自调用问题。<br>
若同一类中的其他没有@Transactional 注解的方法内部调用有@Transactional 注解的方法，有@Transactional 注解的方法的事务被忽略，不会发生回滚。见清单 5 举例代码展示。

`清单 5`.自调用问题举例
```java
@Service
public class OrderService {
    
    private void insert() {
insertOrder();
}
    
    @Transactional
    public void insertOrder() {
        //insert log info  
    }
}
```
insertOrder 尽管有@Transactional 注解，但它被内部方法 insert 调用，事务被忽略，出现异常事务不会发生回滚。<br>
上面的两个问题@Transactional 注解只应用到 public 方法和自调用问题，是由于使用 Spring AOP 代理造成的。<br>
为解决这两个问题，使用 AspectJ 取代 Spring AOP 代理。<br>
需要将下面的 AspectJ 信息添加到 xml 配置信息中。<br>

`清单 6`. AspectJ 的 xml 配置信息
```xml 
<tx:annotation-driven mode="aspectj" />
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
     <property name="dataSource" ref="dataSource" />
</bean>
</bean class="org.springframework.transaction.aspectj.AnnotationTransactionAspect" factory-method="aspectOf">
      <property name="transactionManager" ref="transactionManager" />
</bean>
```
同时在 Maven 的 pom 文件中加入 spring-aspects 和 aspectjrt 的 dependency 以及 aspectj-maven-plugin。

`清单 7`. AspectJ 的 pom 配置信息
```xml 
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-aspects</artifactId>
    <version>4.3.2.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <art``ifactId>aspectjrt</artifactId>
    <version>1.8.9</version>
</dependency>
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.9</version>
    <configuration>
        <showWeaveInfo>true</showWeaveInfo>
        <aspectLibraries>
            <aspectLibrary>
                <groupId>org.springframework</groupId>
                <artifactId>spring-aspects</artifactId>
            </aspectLibrary>
        </aspectLibraries>
    </configuration>
    <executions>
        <execution>
        <goals>
        <goal>compile</goal>
        <goal>test-compile</goal>
        </goals>
        </execution>
    </executions>
</plugin>
```
## Spring AOP 实现原理

### Spring AOP 代理的实现

#### **`动态代理`**：    利用核心类Proxy和接口InvocationHandler(基于代理模式的思想)
```java
public interface StudentInterface {
    void username();
} 
public class Student implements StudentInterface{

    private String name;

	public Student(){}

	public Student(String name){
		this.name = name;
	}

	public void username(){
		System.out.println("学生的名称："+name);
	}

	public String getName() {    
		return name;    
	}

	public void setName(String name) {    
		this.name = name;    
	}    
}    
public class ProxyFactory implements   InvocationHandler {

    private static Logger logger = LoggerFactory.getLogger(ProxyFactory.class);

    private Object object;

    public ProxyFactory() {
    }

    public Object createProxy(Object object){
		this.object = object;
		return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), this);
	} 
	@Override    
	public Object invoke(Object proxy, Method method, Object[] args)
			            throws Throwable {
			object = method.invoke(object, args);
		return object;    
	} 
}

public class TestProxy {

    public static void main(String args[]) {
        ProxyFactory proxyFactory = new ProxyFactory();
        Student student = new Student("runcoding");
        StudentInterface studentBean = (StudentInterface) proxyFactory.createProxy(student);
		studentBean.username();
    }

} 

```

#### **`字节码生成`**：  利用CGLIB动态字节码库

```java
public class StudentCglib {

    private String name;

	public StudentCglib(){

	}

	public StudentCglib(String name){
		this.name = name;
	}

	public void print(){
		System.out.println(name +" hello world!");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;    
	}    
} 
public class CGlibProxyFactory implements MethodInterceptor {

    private Object object;

	public Object createProxy(Object object){
		this.object = object;    
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(object.getClass());    
		enhancer.setCallback(this);    
		return enhancer.create();    
	}

	@Override    
	public Object intercept(Object proxy, Method method, Object[] args,
							MethodProxy methodProxy) throws Throwable {
	    if(object instanceof  StudentCglib){
            System.out.println("方法已经被拦截...");
        }
        return methodProxy.invoke(object, args);
	}    
}   
public class TestCGlibProxy {
    public static void main(String[] args) {

		StudentCglib stu1 = (StudentCglib)(new CGlibProxyFactory().createProxy(new StudentCglib()));

		StudentCglib stu2 = (StudentCglib)(new CGlibProxyFactory().createProxy(new StudentCglib("Running Coding")));
		stu1.print();    
		stu2.print();    
	}    
}   
```

### Spring AOP中的关键字
- 1.Joinpoint
- 2.Pointcut 
<a href='//www.jianshu.com/p/f142b69e3823'>详细介绍</a>
 
### Spring-bean 初始化过程
- https://zhuxingsheng.github.io/2017/05/24/spring-bean-initialization-proces/
- https://zhuxingsheng.github.io/2017/05/24/spring-bean初始化过程/ 
 
 
 
 