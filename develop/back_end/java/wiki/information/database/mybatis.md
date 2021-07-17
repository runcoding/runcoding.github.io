## Mybaits提供一级缓存，和二级缓存
@See //blog.csdn.net/u012373815/article/details/47069223

### **一级缓存是SqlSession级别的缓存**。

在操作数据库时需要构造 sqlSession对象，在对象中有一个(内存区域)数据结构（HashMap）用于存储缓存数据。<br>
不同的sqlSession之间的缓存数据区域（HashMap）是互相不影响的。<br>
一级缓存的作用域是同一个SqlSession，在同一个sqlSession中两次执行相同的sql语句，第一次执行完毕会将数据库中查询的数据写到缓存（内存），<br>
第二次会从缓存中获取数据将不再从数据库查询，从而提高查询效率。当一个sqlSession结束后该sqlSession中的一级缓存也就不存在了。Mybatis默认开启一级缓存。<br>

`注意：` 如果是执行两次service调用查询相同的用户信息，不走一级缓存，因为Service方法结束，sqlSession就关闭，一级缓存就清空。
### **二级缓存是mapper级别的缓存**
 多个SqlSession去操作同一个Mapper的sql语句，多个SqlSession去操作数据库得到数据会存在二级缓存区域，多个SqlSession可以共用二级缓存，二级缓存是跨SqlSession的。<br>
 二级缓存是多个SqlSession共享的，其作用域是mapper的同一个namespace，不同的sqlSession两次执行相同namespace下的sql语句且向sql中传递参数也相同即最终执行相同的sql语句，<br>
 第一次执行完毕会将数据库中查询的数据写到缓存（内存），第二次会从缓存中获取数据将不再从数据库查询，从而提高查询效率。<br>
 Mybatis默认没有开启二级缓存需要在setting全局参数中配置开启二级缓存。如果缓存中有数据就不用从数据库中获取，大大提高系统性能。
 
 ### Mybatis二级缓存原理
 @See //www.jianshu.com/p/5ff874fa696f 
 <ul>
 <li>学会对Mybatis配置二级缓存</li>
 <li>学会Mybatis二级缓存的实现方式</li>
 <li>学会整合外部缓存框架(如:<a href="//www.ehcache.org/" target="_blank"><em>Ehcache</em></a>)</li>
 <li>学会自定义二级缓存</li>
 </ul>
 
#### 1. Mybatis内部二级缓存的配置
 
 <p>要使用Mybatis的二级缓存，需要对Mybatis进行配置，配置分三步</p>
 <ul>
 <li>Mybatis全局配置中启用二级缓存配置<pre class="hljs undefined"><code>&lt;setting name="cacheEnabled" value="true"/&gt;</code></pre>
 </li>
 <li>在对应的Mapper.xml中配置cache节点<pre class="hljs undefined"><code>&lt;mapper namespace="userMapper"&gt;
   &lt;cache /&gt;
   &lt;result ... /&gt;
   &lt;select ... /&gt;
 &lt;/mapper&gt;</code></pre>
 </li>
 <li>
 <p>在对应的select查询节点中添加useCache=true</p>
 <pre class="hljs undefined"><code>&lt;select id="findUserById" parameterType="int" resultMap="user" useCache="true"&gt;
   select * from users where id=#{id};
 &lt;/select&gt;</code></pre>
 </li>
 <li>
 <p>高级配置</p>
 <blockquote><p>a. 为每一个Mapper分配一个Cache缓存对象（使用&lt;cache&gt;节点配置）<br>b. 多个Mapper共用一个Cache缓存对象（使用&lt;cache-ref&gt;节点配置）</p></blockquote>
 </li>
 </ul>
 <p>只要简单的三步配置即可开启Mybatis的二级缓存了。在使用mybatis查询时候("userMapper.findUserById")，不同会话(Sqlsession)在查询时候，只会查询数据库一次，第二次会从二级缓存中读取。</p>

```` java
 @Before
 public void before() {
     String mybatisConfigFile = "MybatisConfig/Mybatis-conf.xml";
     InputStream stream = TestMybatis.class.getClassLoader().getResourceAsStream(mybatisConfigFile);
     sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream); //构建sqlSession的工厂
 }
 @Test
 public void test() {
     SqlSession sqlSession = sqlSessionFactory.openSession();
     User i = sqlSession.selectOne("userMapper.findUserById", 1);
     System.out.println(i);
     sqlSession.close();
     sqlSession = sqlSessionFactory.openSession();
     User x = sqlSession.selectOne("userMapper.findUserById", 1); // 读取二级缓存数据
     System.out.println(x);
     sqlSession.close();
 } 
````
#### 2. Mybatis内部二级缓存的设计及工作模式

 <div class="image-package">
 <img src="//upload-images.jianshu.io/upload_images/3167863-62a2bf5438197d58.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" data-original-src="//upload-images.jianshu.io/upload_images/3167863-62a2bf5438197d58.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2" style="cursor: zoom-in;"><br><div class="image-caption"></div>
 </div><p><br>首先我们要知道，mybatis的二级缓存是通过CacheExecutor实现的。CacheExecutor其实是Executor的代理对象。所有的查询操作，在CacheExecutor中都会先匹配缓存中是否存在，不存在则查询数据库。</p>
  
#### 3. 内部二级缓存的实现详解
 <p>竟然知道Mybatis二级缓存是通过CacheExecotur实现的，那看下Mybatis中创建Executor的过程</p>
 <pre class="hljs undefined"><code>// 创建执行器(Configuration.newExecutor)
 public Executor newExecutor(Transaction transaction, ExecutorType executorType) {
    //确保ExecutorType不为空(defaultExecutorType有可能为空)
    executorType = executorType == null ? defaultExecutorType : executorType;
    executorType = executorType == null ? ExecutorType.SIMPLE : executorType;
    Executor executor;
    if (ExecutorType.BATCH == executorType) {
       executor = new BatchExecutor(this, transaction);
    } else if (ExecutorType.REUSE == executorType) {
       executor = new ReuseExecutor(this, transaction);
    } else {
       executor = new SimpleExecutor(this, transaction);
    }
    if (cacheEnabled) { //重点在这里，如果启用全局代理对象，返回Executor的Cache包装类对象
       executor = new CachingExecutor(executor);
    }
    executor = (Executor) interceptorChain.pluginAll(executor);
    return executor;
 }</code></pre>
 <p>重点在cacheEnabled这个参数。如果你看了我的文章[<a href="//www.jianshu.com/p/82f0875ac22f" target="_blank">Mybatis配置文件解析过程详解</a>]，就应该知道了怎么设置cacheEnabled。对，就是此文章第一点说的开启Mybatis的全局配置项。我们继续看下CachingExecutor具体怎么实现的。</p>
 <pre class="hljs undefined"><code>public class CachingExecutor implements Executor {
     private Executor delegate;
     public CachingExecutor(Executor delegate) {
         this.delegate = delegate;
         delegate.setExecutorWrapper(this);
     }
     public int update(MappedStatement ms, Object parameterObject) throws SQLException {
         flushCacheIfRequired(ms); //是否需要更缓存
         return delegate.update(ms, parameterObject);  //更新数据
     }
     ......
 }</code></pre>
 <p>很清晰，静态代理模式。在CachingExecutor的所有操作都是通过调用内部的delegate对象执行的。缓存只应用于查询，我们看下CachingExecutor的query方法。</p>
 <pre class="hljs undefined"><code>public &lt;E&gt; List&lt;E&gt; query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
     BoundSql boundSql = ms.getBoundSql(parameterObject);
     //创建缓存值
     CacheKey key = createCacheKey(ms, parameterObject, rowBounds, boundSql);
     //获取记录
     return query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
 }
 
 public &lt;E&gt; List&lt;E&gt; query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql)
         throws SQLException {
     Cache cache = ms.getCache();
     if (cache != null) {
         flushCacheIfRequired(ms);
         if (ms.isUseCache() &amp;&amp; resultHandler == null) {
             // ensureNoOutParams
             if (ms.getStatementType() == StatementType.CALLABLE) {
                 for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
                     if (parameterMapping.getMode() != ParameterMode.IN) {
                         throw new ExecutorException("Caching stored procedures with OUT params is not supported.  Please configure useCache=false in " + ms.getId() + " statement.");
                     }
                 }
             }
             List&lt;E&gt; list = (List&lt;E&gt;) tcm.getObject(cache, key); //从缓存中获取数据
             if (list == null) {
                 list = delegate.&lt;E&gt;query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
                 tcm.putObject(cache, key, list); // 结果保存到缓存中
             }
             return list;
         }
     }
     return delegate.&lt;E&gt;query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
 }</code></pre>
 <p>如果MappedStatement中对应的Cache存在，并且对于的查询开启了二级缓存(useCache="true")，那么在CachingExecutor中会先从缓存中根据CacheKey获取数据，如果缓存中不存在则从数据库获取。这里的代码很简单，很容易理解。<br>说到缓存，有效期和缓存策略不得不提。在Mybatis中二级缓存也实现了有效期的控制和缓存策略。Mybatis中是使用装饰模式实现的，具体可以看下mybatis的cache包<br></p><div class="image-package">
 <img src="//upload-images.jianshu.io/upload_images/3167863-476daef898ab2728.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" data-original-src="//upload-images.jianshu.io/upload_images/3167863-476daef898ab2728.png?imageMogr2/auto-orient/strip%7CimageView2/2" style="cursor: zoom-in;"><br><div class="image-caption"></div>
 </div><p><br>具体于配置如下：</p>
 <pre class="hljs undefined"><code>&lt;cache eviction="FIFO|LRU|SOFT|WEAK" flushInterval="300" size="100" /&gt;</code></pre>
 <p>对应具体实现源码可以参考CacheBuilder类的源码。</p>
 <pre class="hljs undefined"><code>public Cache build() {
     if (implementation == null) { //缓存实现类
         implementation = PerpetualCache.class;
         if (decorators.size() == 0) {
             decorators.add(LruCache.class);
         }
     }
     Cache cache = newBaseCacheInstance(implementation, id);
     setCacheProperties(cache);
     if (PerpetualCache.class.equals(cache.getClass())) {
         for (Class&lt;? extends Cache&gt; decorator : decorators) {
             cache = newCacheDecoratorInstance(decorator, cache);
             setCacheProperties(cache);
         }
         // 采用默认缓存包装类
         cache = setStandardDecorators(cache);
     } else if (!LoggingCache.class.isAssignableFrom(cache.getClass())) {
         cache = new LoggingCache(cache);
     }
     return cache;
 }</code></pre>
  
#### 4. 一级缓存和二级缓存的使用顺序
 <p>如果你的MyBatis使用了二级缓存，并且你的Mapper和select语句也配置使用了二级缓存，那么在执行select查询的时候，MyBatis会先从二级缓存中取输入，其次才是一级缓存，即MyBatis查询数据的顺序是：</p>
 <blockquote><p>二级缓存    ———&gt; 一级缓存——&gt; 数据库</p></blockquote>
 
####  5. mybatis二级缓存和分页插件同时使用产生的问题
 <p>问题：分页插件开启二级缓存后，分页查询时无论查询哪一页都返回第一页的数据</p>
 <blockquote><p>在之前讲解Mybatis的执行流程的时候提到，在开启cache的前提下，Mybatis的executor会先从缓存里读取数据，读取不到才去数据库查询。问题就出在这里，sql自动生成插件和分页插件执行的时机是在statementhandler里，而statementhandler是在executor之后执行的，无论sql自动生成插件和分页插件都是通过改写sql来实现的，executor在生成读取cache的key（key由sql以及对应的参数值构成）时使用都是原始的sql，这样当然就出问题了。<br>找到问题的原因后，解决起来就方便了。只要通过拦截器改写executor里生成key的方法，在生成可以时使用自动生成的sql（对应sql自动生成插件）或加入分页信息（对应分页插件）就可以了。<br>参考:<a href="//blog.csdn.net/hupanfeng/article/details/16950161" target="_blank">//blog.csdn.net/hupanfeng/article/details/16950161</a></p></blockquote>
 6. mybatis整合第三方缓存框架
 <div class="image-package">
 <img src="//upload-images.jianshu.io/upload_images/3167863-860765ed0fdbcd3e.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" data-original-src="//upload-images.jianshu.io/upload_images/3167863-860765ed0fdbcd3e.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2" style="cursor: zoom-in;"><br><div class="image-caption"></div>
 </div><p><br>我们以ehcache为例。对于ehcache我只会简单的使用。这里我只是介绍Mybatis怎么使用ehcache，不对ehcache配置作说明。我们知道，在配置二级缓存时候，我们可以指定对应的实现类。这里需要mybatis-ehcache-1.0.3.jar这个jar包。在Mapper中我们只要配置如下即可。</p>
 <pre class="hljs undefined"><code>&lt;cache type="org.mybatis.caches.ehcache.EhcacheCache"/&gt;</code></pre>
 <p>当然，项目中ehcache的配置还是需要的。</p>
 小结
 <blockquote><p>对于Mybatis整合第三方的缓存，实现骑士很简单，只要在配置的地方制定实现类即可。<br>Mybatis默认二级缓存的实现在集群或者分布式部署下是有问题的，Mybatis默认缓存只在当节点内有效，并且对缓存的失效操作无法同步的其他节点。需要整合第三方分布式缓存实现，如ehcache或者自定义实现。</p></blockquote> 