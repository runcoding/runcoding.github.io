#### 文档参考来源
@参考来源：<a href='https://hanxlinsist.github.io/Spring-MVC%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90/#more'>https://hanxlinsist.github.io/Spring MVC源码剖析</a>


#### Spring MVC 总览

学一门新的知识，首先要大致了解它的全貌，然后在深入自己感兴趣的细节。
那么在这一小节中，我不会去深入具体的细节了解spring MVC，而是去了解它大致的流程，它是如何工作起来的。<br>
首先，让我们先看看下面这张流程图。
<img src="https://runcoding.github.io/static/wiki/learn-java/java/spring-mvc/1st.png" >

相信用过spring MVC的人都知道，客户端的请求要通过前端控制器(DispatcherServlet)，然后前端控制器去找到请求相应的Controller。<br>
因此我们可以猜到前端控制器一定要在Servlet容器启动时被实例化，所以我们需要把DispatcherServlet配置到web.xml文件中，部分配置如下：

 ````xml
<servlet>
	<servlet-name>spring</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring.xml</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
````
 
#### DispatcherServlet中的静态代码块
```` java
源码如下： 
/**
 * Name of the class path resource (relative to the DispatcherServlet class)
 * that defines DispatcherServlet's default strategy names.
 */
private static final String DEFAULT_STRATEGIES_PATH = "DispatcherServlet.properties";
private static final Properties defaultStrategies;
static {
	// Load default strategy implementations from properties file.
	// This is currently strictly internal and not meant to be customized
	// by application developers.
	try {
		ClassPathResource resource = new ClassPathResource(DEFAULT_STRATEGIES_PATH, DispatcherServlet.class);
		defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);
	}
	catch (IOException ex) {
		throw new IllegalStateException("Could not load 'DispatcherServlet.properties': " + ex.getMessage());
	}
}
````
当一个类被JVM加载、链接过后，JVM会调用类构造器会初始化一些静态域对象。<br>
因此上面的静态代码块会被执行，其中的代码只有一个目的，那就是从属性文件中加载默认的strategy实现，最后赋值给defaultStrategies 变量。<br>
上面的注释也说明了属性文件的位置，大家可以用解压工具把spring-webmvc jar包用解压工具打开。我打开结果如下图：<br>
<img src="https://runcoding.github.io/static/wiki/learn-java/java/spring-mvc/2nd.png" > 

大家可以打开DispatcherServlet.properties 文件，可以看到如下内容：
<img src="https://runcoding.github.io/static/wiki/learn-java/java/spring-mvc/dispatcherservlet.png" > 

在context成功的refresh过后，onRefresh 方法就会被调用，然后它会调用initStrategies 方法。<br>
下面让我们来看看initStrategies 方法具体都初始化哪些strategy对象。<br>

#### strategy对象的初始化
```` java
源码如下： 
/**
 * Initialize the strategy objects that this servlet uses.
 * <p>May be overridden in subclasses in order to initialize further strategy objects.
 */
protected void initStrategies(ApplicationContext context) {
	initMultipartResolver(context);
	initLocaleResolver(context);
	initThemeResolver(context);
	initHandlerMappings(context);
	initHandlerAdapters(context);
	initHandlerExceptionResolvers(context);
	initRequestToViewNameTranslator(context);
	initViewResolvers(context);
	initFlashMapManager(context);
}
````
对于上面的各个初始化方法，我只讲解几个与我们开发者最密切的初始化方法，它们分别是initHandlerMappings initHandlerAdapters initHandlerExceptionResolvers initViewResolvers。 如果大家对其它方法感兴趣，自己去查看一下相应的源码。下面让我们来深入各个初始化方法的细节。

#### initHandlerMappings 方法

这个方法的作用是初始化我们程序将要用到的HandlerMapping对象，下面让我们来看看源码。源码如下：

````java  
/** List of HandlerMappings used by this servlet */
private List<HandlerMapping> handlerMappings;
/** Detect all HandlerMappings or just expect "handlerMapping" bean? */
private boolean detectAllHandlerMappings = true;
/**
 * Initialize the HandlerMappings used by this class.
 * <p>If no HandlerMapping beans are defined in the BeanFactory for this namespace,
 * we default to BeanNameUrlHandlerMapping.
 */
private void initHandlerMappings(ApplicationContext context) {
	this.handlerMappings = null;
	if (this.detectAllHandlerMappings) {
		// Find all HandlerMappings in the ApplicationContext, including ancestor contexts.
		Map<String, HandlerMapping> matchingBeans =
				BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);
		if (!matchingBeans.isEmpty()) {
			this.handlerMappings = new ArrayList<HandlerMapping>(matchingBeans.values());
			// We keep HandlerMappings in sorted order.
			OrderComparator.sort(this.handlerMappings);
		}
	}
	else {
		try {
			HandlerMapping hm = context.getBean(HANDLER_MAPPING_BEAN_NAME, HandlerMapping.class);
			this.handlerMappings = Collections.singletonList(hm);
		}
		catch (NoSuchBeanDefinitionException ex) {
			// Ignore, we'll add a default HandlerMapping later.
		}
	}
	// Ensure we have at least one HandlerMapping, by registering
	// a default HandlerMapping if no other mappings are found.
	if (this.handlerMappings == null) {
		this.handlerMappings = getDefaultStrategies(context, HandlerMapping.class);
		if (logger.isDebugEnabled()) {
			logger.debug("No HandlerMappings found in servlet '" + getServletName() + "': using default");
		}
	}
}
````

在了解上面代码的意义之前，让我们来看看我的spring.xml 文件中都配置了什么。
```` xml
<context:component-scan base-package="me.xurtle" />
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<property name="prefix" value="/" />
	<property name="suffix" value=".jsp" />
</bean>
````

大家可以看到，我没有在spring的配置文件配置任何的HandlerMapping对象。<br>
从上面的代码我们可以看出detectAllHandlerMappings默认为true，也就是默认会去检测配置文件中所有的HandlerMapping，<br>
接下来是beansOfTypeIncludingAncestors 方法，如果你去查看这个方法的源码，你会看到它的第一个参数给定的是ListableBeanFactory 接口，<br>
对于这个接口的实现类来说，它可以枚举出所有定义的bean实例，而不是仅仅可以通过bean名称去获得定义的bean实例。<br>
那么beansOfTypeIncludingAncestors 具体的做法就是枚举出所有定义的bean，筛选出给定类型或其子类的bean，如果第一个参数是HierarchicalBeanFactory 的子类，<br>
它也会去查找父类工厂中的HandlerMapping，最后返回一个Map 对象。<br>

如果返回的Map 对象中存在HandlerMapping对象，那么接下来会把这些对象存入到ArrayList 中。<br>
同样它也会保持HandlerMapping对象在集合中的顺序，大家可能会觉得奇怪，为什么要保持这些对象的顺序呢？其实很简单，等我下面分析到映射请求的时候，大家就会明白了。

如果一直都没有获得到HandlerMapping对象，那么接下来的getDefaultStrategies 方法会给我们生成一些默认的HandlerMapping对象。<br>
其实这个方法也很简单，它其实就是用我们给定的strategy对象接口的名字作为key，接着去属性文件中加载对应的值。<br>
而这个属性文件就是上面我分析的静态代码块中初始化的那个属性文件。接着它会用StringUtils 工具类把拿到的value根据逗号分开存入到String 数组中，<br>
然后遍历这些名字，根据相应的名字加载相应的类，创建相应的对象。<br>
不得不说，这个方法的实现真的很好，它只写这一个方法，就可以加载不同的strategy对象，实现了代码的重用，值得我们学习。

如果你Debug一下程序，你可以看到handlerMappings 列表中其实有两个实例，第一个是BeanNameUrlHandlerMapping ，<br>
第二个是DefaultAnnotationHandlerMapping ，就象我前面说的那样，Spring MVC会使用列表中的第一个对象，即BeanNameUrlHandlerMapping 的对象，<br>
如果通过这个对象并没有找到相应的handler，然后才会使用DefaultAnnotationHandlerMapping 的对象。

至此，我们还一个逻辑没有介绍。如果想让initHandlerMappings 方法走这个逻辑，我们需要把detectAllHandlerMappings 设置为false. <br>
其实这个很简单，只要在web.xml 文件中配置一下就行了。部分代码如下：
```` xml 
<servlet>
	<servlet-name>spring</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring.xml</param-value>
	</init-param>
	<init-param>
		<param-name>detectAllHandlerMappings</param-name>
		<param-value>false</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
````
除了上面的代码外，我们还需要在spring.xml 文件中定义一个HandlerMapping，部分代码如下：
````xml
<bean name="handlerMapping" class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping" />
````
你的bean名称一定为handlerMapping ，不可以是其它的值。如果你把这个名称指定为其它的值，Spring框架不能得到这个bean，它依然会给你默认的HandlerMapping对象。<br>

`_总结_`：通过上面源码的分析可以看出，在spring配置文件中明确指定一个bean名称为handlerMapping的做法更有效，<br>
因为它不需要去遍历配置文件中所有的bean，所以这个做法会加快initHandlerMappings方法的执行。<br>

#### initHandlerAdapters 方法

这个方法的作用是初始化我们程序将要用到的HandlerAdapter对象，下面让我们来看看源码。源码如下：
```` java
/** List of HandlerAdapters used by this servlet */
private List<HandlerAdapter> handlerAdapters;
/** Detect all HandlerAdapters or just expect "handlerAdapter" bean? */
private boolean detectAllHandlerAdapters = true;
/**
 * Initialize the HandlerAdapters used by this class.
 * <p>If no HandlerAdapter beans are defined in the BeanFactory for this namespace,
 * we default to SimpleControllerHandlerAdapter.
 */
private void initHandlerAdapters(ApplicationContext context) {
	this.handlerAdapters = null;
	if (this.detectAllHandlerAdapters) {
		// Find all HandlerAdapters in the ApplicationContext, including ancestor contexts.
		Map<String, HandlerAdapter> matchingBeans =
				BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerAdapter.class, true, false);
		if (!matchingBeans.isEmpty()) {
			this.handlerAdapters = new ArrayList<HandlerAdapter>(matchingBeans.values());
			// We keep HandlerAdapters in sorted order.
			OrderComparator.sort(this.handlerAdapters);
		}
	}
	else {
		try {
			HandlerAdapter ha = context.getBean(HANDLER_ADAPTER_BEAN_NAME, HandlerAdapter.class);
			this.handlerAdapters = Collections.singletonList(ha);
		}
		catch (NoSuchBeanDefinitionException ex) {
			// Ignore, we'll add a default HandlerAdapter later.
		}
	}
	// Ensure we have at least some HandlerAdapters, by registering
	// default HandlerAdapters if no other adapters are found.
	if (this.handlerAdapters == null) {
		this.handlerAdapters = getDefaultStrategies(context, HandlerAdapter.class);
		if (logger.isDebugEnabled()) {
			logger.debug("No HandlerAdapters found in servlet '" + getServletName() + "': using default");
		}
	}
}
````

看完上面的代码是否有种似曾相识的感觉？对的，上面代码的逻辑和initHandlerMappings 一样，同样的你可以设置detectAllHandlerAdapters 来改变代码的逻辑，<br>
你也可以看看我上面给你的默认strategy属性文件，就可以知道Spring MVC给你加载了哪些默认的HandlerAdapter对象。<br>
只要你理解了我上面initHandlerMappings 方法的分析，这个方法就没有什么可说的了，和它一样。<br>

#### initHandlerExceptionResolvers 方法

这个方法中初始化的对象都为HandlerExceptionResolver 的子类，对于Controller中出现的异常，会调用 processHandlerException方法来统一处理异常。<br>
稍后我会详细介绍这些对象在处理异常时扮演的角色，大家现在有个印象就行。<br>
还有一点大家应该注意的就是，如果你自己并没有定义一个处理HandlerExceptionResolver 的子类来处理异常，即使Spring MVC给你加载了默认的子类，它们也不会帮你处理异常的。<br>
这个方法的执行逻辑和上面的一样，这里我也就不多说了。<br>

#### initViewResolvers 方法

逻辑依然和上面的初始化方法一样，你可以实现ViewResolver 接口来定义自己的解析视图的方法。<br>
如果你并没有指定自己的类，那么默认的类为 InternalResourceViewResolver。<br>

#### DispatcherServlet如何“统领指挥”？
上面的初始化方法都是为真正的“战役”做准备的。在这一小节中，我会带大家看看DispatcherServlet是如何调动“千军万马”来打仗（处理来自客户端的请求）的。<br>

正如它的名字一样，DispatcherServlet 也是一个Servlet，它间接继承自HttpServlet ，它也重写了doService 方法。<br>
当从客户端发出一个请求时，它会首先执行它的doService 方法，如果大家去看看这个方法，它其实就是在requset 域中发布一些属性，然后调用doDispatch 方法。<br>
这个方法才是实际做事情的方法。

下面，来让我们看看doDispatch 方法的真面目吧。由于这个方法中涉及到的方法很多并且它本身的方法也很长，因此在这一小节中我不会整段整段的复制代码，<br>
而是抽出重要的代码片段，对于一些方法来说，我也会去除掉一些没有用的代码，比如记录日志的代码。因此我建议大家打开自己的源码结合着下文一起看。<br>

#### 找到当前请求的handler

从下面的代码中可以看到，doDispatch 方法中调用getHandler 方法找到相应请求的Handler，奇怪的是，返回的是一个HandlerExecutionChain对象，<br>
其实它很好理解，只不过是框架把找到的Handler（即我们处理请求的Controller）和一些个拦截器包装到这个对象中。<br>
```` java
HandlerExecutionChain mappedHandler = null;
mappedHandler = getHandler(processedRequest, false);
/**
 * Return the HandlerExecutionChain for this request.
 * <p>Tries all handler mappings in order.
 * @param request current HTTP request
 * @return the HandlerExecutionChain, or <code>null</code> if no handler could be found
 */
protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
	for (HandlerMapping hm : this.handlerMappings) {
		HandlerExecutionChain handler = hm.getHandler(request);
		if (handler != null) {
			return handler;
		}
	}
	return null;
}
````

上面代码中的getHandler方法实际就是遍历你上面初始化的HandlerMapping 对象，然后用其找到相应的Handler，<br>
大家可以看到它的返回值实际是HandlerExecutionChain 对象，如果大家继续Debug程序，它其实会把你定义的Handler和拦截器包装起来，一并返回。<br>

还有一点我想强调的是，每个HandlerMapping 找到Controller 的方式不同，如果大家自己跟踪一下断点，<br>
发现无论哪个HandlerMapping 最终都会到AbstractUrlHandlerMapping 类中的lookupHandler 方法，这个类中其实还有个私有变量handlerMap ，<br>
这个变量在容器启动的时候，Spring MVC已经把URL作为key，对应的Controller作为value存入到这个变量中。<br>
下面我给大家举个例子，假设现在我们定义的HandlerMapping 为DefaultAnnotationHandlerMapping 对象，下面我来介绍一下它映射URL到Controller大致的流程。<br>

当Servlet容器启动时，Spring MVC会实例化你所有定义的Bean，当然这绝对包括DefaultAnnotationHandlerMapping 对象<br>
在实例化的过程中，它会调用AbstractDetectingUrlHandlerMapping 中的detectHandlers 方法，这个方法中会遍历你所有的Bean对象，<br>
甚至都会包括DefaultAnnotationHandlerMapping 对象，从而找到URL以及相对应的Handler<br>

在第2步中的detectHandlers 方法中有一个determineUrlsForHandler 方法，这个方法有主要作用是为给定的bean找到相应的URL，<br>
这个方法是抽象的，因此它会分派给具体的子类做这件事情，因为我用的是DefaultAnnotationHandlerMapping 对象，所以会调用它里面的determineUrlsForHandler 方法<br>
DefaultAnnotationHandlerMapping 中的 determineUrlsForHandler 方法首先会查看你给定的bean对象上是否有RequestMapping 注解，<br>
然后会调用determineUrlsForHandlerMethods 方法查找你这个bean中的方法上是否有RequestMapping 注解<br>

如果在第4步中得到了一个URL和Controller的映射，那么AbstractDetectingUrlHandlerMapping 中的detectHandlers 方法会调用AbstractUrlHandlerMapping 中的 registerHandler 方法，把这个映射会放入它的域变量handlerMap 中<br>

从上面的步骤中可以看出，Spring MVC在容器启动完毕以后就已经把所有的URL和Controller的映射放入到AbstractUrlHandlerMapping 中的handlerMap 域变量中，<br>
当请求到来时，它用URL当作key来取得对应的Controller就行了。<br>
BeanNameUrlHandlerMapping 和 DefaultAnnotationHandlerMapping 是同样的道理，只不过它们的determineUrlsForHandler 方法不同。<br>

SimpleUrlHandlerMapping 这个类有点和它们不一样，它是基于配置把URL与Controller对应起来，而不是用determineUrlsForHandler 方法来获取映射。<br>
剩下的步骤就一样了，用它里面的registerHandlers 方法把这些映射注册到它的父类AbstractUrlHandlerMapping 中。<br>
还有一些其它的HandlerMapping 都是大同小异，这里我就不过多解释了。<br>

#### 找到HandlerAdapter

上面已经根据URL找到相应的Handler，接下来我们需要找到与当前Handler相匹配的HandlerAdapter 来调用方法处理请求。<br>
大家可能会想，我们已经找到了相应的Handler， 直接调用它里面的方法处理请求不就行了吗？这样做是肯定不行的。<br>
如果大家对Spring MVC熟悉的话，就会知道定义一个Controller 可以有要多种多样的方式。比如，我们可以实现Controller 接口，<br>
也可以用注解的方式来定义Controller， 因此不同的定义方式会导致不同的调用方式。现在让我们来看看源码是怎么做的吧。<br>
```` java 
HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
/**
 * Return the HandlerAdapter for this handler object.
 * @param handler the handler object to find an adapter for
 * @throws ServletException if no HandlerAdapter can be found for the handler. This is a fatal error.
 */
protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
	for (HandlerAdapter ha : this.handlerAdapters) {
		if (ha.supports(handler)) {
			return ha;
		}
	}
}
````
源码看起来很简单，调用getHandlerAdapter 方法就可以得到一个HandlerAdapter， 但是实际上并没有这么简单。<br>
这个方法就是遍历集合中的HandlerAdapter， 找到支持当前Handler 的一个HandlerAdapter ，那么怎么才算支持呢？实际上不同的HandlerAdapter 所支持的方式不一样。<br>
下面，我拿出2个（AnnotationMethodHandlerAdapter 和 SimpleControllerHandlerAdapter）大家最熟悉的HandlerAdapter 的子类来看看到底哪里不一样？<br>

如果是SimpleControllerHandlerAdapter，这个就很简单了，它的supports 方法只是去看看当前的Handler 是否为Controller 接口的实例，如果是就支持。<br>

如果是AnnotationMethodHandlerAdapter，这个就有点复杂了。下面是具体的源码。<br>

```` java
private final Map<Class<?>, ServletHandlerMethodResolver> methodResolverCache =
		new ConcurrentHashMap<Class<?>, ServletHandlerMethodResolver>();
			
public boolean supports(Object handler) {
    return getMethodResolver(handler).hasHandlerMethods();
}
/**
 * Build a HandlerMethodResolver for the given handler type.
 */
private ServletHandlerMethodResolver getMethodResolver(Object handler) {
	Class handlerClass = ClassUtils.getUserClass(handler);
	ServletHandlerMethodResolver resolver = this.methodResolverCache.get(handlerClass);
	if (resolver == null) {
		synchronized (this.methodResolverCache) {
			resolver = this.methodResolverCache.get(handlerClass);
			if (resolver == null) {
				resolver = new ServletHandlerMethodResolver(handlerClass);
				this.methodResolverCache.put(handlerClass, resolver);
			}
		}
	}
	return resolver;
}
````

在理解上面的代码之前，让我们先看看ServletHandlerMethodResolver 和 HandlerMethodResolver 这2个类。<br>
ServletHandlerMethodResolver 是 AnnotationMethodHandlerAdapter 的内部类，它继承了HandlerMethodResolver。<br>

在实例化ServletHandlerMethodResolver 的同时，它会调用HandlerMethodResolver 中的init()方法，<br>
在这个init()方法中会解析所有带有RequestMapping 注解的方法，并把它存入到它的域变量handlerMethods中。<br>
 每个Handler 都对应着一个ServletHandlerMethodResolver 实例，这个实例中包含着一切关于当前Handler 中的方法信息。<br>

那么上面代码中的supports 方法首先做的就是调用getMethodResolver 方法，用一个Handler 实例去获取其对应的一个ServletHandlerMethodResolver 实例，<br>
如果存在这个实例，直接返回，如果不存在，新建一个实例，并把它放入到Map 缓存中。P.S. getMethodResolver 方法的同步代码写的很漂亮，直得学习。<br>

总结来说，当第一次用到Handler 中的方法处理请求时，它会一次性解析里面会用到的方法，存到一个ServletHandlerMethodResolver 实例（当然了，这个实例不仅仅只有这些信息），<br>
然后用这个Handler 类作为key，用ServletHandlerMethodResolver 实例作为value存入到Map 缓存中，等到下一个请求再一次用到这个Handler 中的方法时，<br>
它直接从这个缓存中取得相应的信息就ok了。<br>

#### 应用注册拦截器的preHandle 方法

这一步没有什么好说的，大家自己看看下面的代码就全明白了。
````java 
int interceptorIndex = -1;
// Apply preHandle methods of registered interceptors.
HandlerInterceptor[] interceptors = mappedHandler.getInterceptors();
if (interceptors != null) {
	for (int i = 0; i < interceptors.length; i++) {
		HandlerInterceptor interceptor = interceptors[i];
		if (!interceptor.preHandle(processedRequest, response, mappedHandler.getHandler())) {
			triggerAfterCompletion(mappedHandler, interceptorIndex, processedRequest, response, null);
			return;
		}
		interceptorIndex = i;
	}
}
````
实际上，除了我们自己的拦截器外，Spring MVC还给了一个拦截器为AbstractUrlHandlerMapping$PathExposingHandlerInterceptor

#### 调用处理请求的方法
```` java 
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
````
上面的代码用相应的HandlerAdapter 来调用Handler 来处理请求了并返回一个ModelAndView 对象。<br>
不同的HandlerAdapter 调用方式也不相同，对于上面我介绍的SimpleControllerHandlerAdapter 来说，它只是把Handler 强转成了一个Controller ，<br>
然后调用handleRequest 方法就行了。 对于AnnotationMethodHandlerAdapter 来说，如果你能理解上面“找到HandlerAdapter”的过程，相信这个也难不倒你。<br>

ModelAndView 是什么呢？它仅仅是一个容器存储Model 和 View，它们是完全不相同的东西，这所以这样做的原因就是在Controller 当中可以用一个返回值同时返回Model 和 View，下面我来举个例子。
```` java
@Controller
public class HelloWorldController {
	@RequestMapping("/hello")
	public ModelAndView helloWorld() {
		String message = "Hello Spring MVC";
		return new ModelAndView("index.jsp", "info", message);
	}
}
````
上面是我Controller 中的代码，它返回的View名称为index.jsp， 而Model为{info=Hello Spring MVC}<br>

应用注册拦截器的postHandle 方法<br>

这一步也没有什么好说的，大家自己看看下面的代码就全明白了。<br>
```` java
// Apply postHandle methods of registered interceptors.
if (interceptors != null) {
	for (int i = interceptors.length - 1; i >= 0; i--) {
		HandlerInterceptor interceptor = interceptors[i];
		interceptor.postHandle(processedRequest, response, mappedHandler.getHandler(), mv);
	}
}
````
在postHandle 方法中，我们可以操纵从Controller 中返回的ModelAndView 对象，你可以替换它，清空它，向里面加入属性等。<br>

渲染ModelAndView<br>

如果ModelAndView 对象不为空，并且没有调用clear 方法清空它，那么接下来它就会被渲染。<br>
```` java
if (mv != null && !mv.wasCleared()) {
	render(mv, processedRequest, response);
	if (errorView) {
		WebUtils.clearErrorRequestAttributes(request);
	}
}
else {
	if (logger.isDebugEnabled()) {
		logger.debug("Null ModelAndView returned to DispatcherServlet with name '" + getServletName() +
				"': assuming HandlerAdapter completed request handling");
	}
}
````
上面的render 方法会从给定的ModelAndView 对象中解析出一个View 对象，然后就调用了render 方法，这个方法是要属于AbstractView 类的。<br>
如果大家看一下这个类的子类，你会发现各种各样的View 对象，实在是太多了，比如：FreeMarkerView VelocityView InternalResourceView. <br>
AbstractView 类中的render 方法的目的就是用给定的Model 来预处理View 对象，把静态属性和request 域中的属性合并到模型当中，<br>
最后把这个合并过后的模型传递到具体子类的renderMergedOutputModel 方法中，进行渲染。<br>

#### 应用注册拦截器的triggerAfterCompletion方法

到达这个阶段，整个doDispatch 方法已经全部完毕了。
````java
triggerAfterCompletion(mappedHandler, interceptorIndex, processedRequest, response, null);
````
#### 异常处理

在上文中提到的initHandlerExceptionResolvers 方法中，我已经提到了关于统一异常的处理。原理就在下面的代码之中。

````java
catch (ModelAndViewDefiningException ex) {
	logger.debug("ModelAndViewDefiningException encountered", ex);
	mv = ex.getModelAndView();
}
catch (Exception ex) {
	Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
	mv = processHandlerException(processedRequest, response, handler, ex);
	errorView = (mv != null);
}
````
上面的代码中有2个异常，一个是ModelAndViewDefiningException ，对于这个异常来说，如果在你的Controller 中抛出了这个异常并给定相应的ModelAndView，<br>
 它依然会在下面的代码中解析这个返回的ModelAndView 对象，如果没有指定ModelAndView，那么程序会出错。对于其它的异常来说，都会被Exception 所捕获，<br>
 接着会用processHandlerException 方法去处理调用我们自己定义的异常处理方法。<br>

#### 总结
至此，我已经把Spring MVC工作的细节以源码的形式说完了。如果Spring MVC是个黒盒，那么这篇文章已经为大家打开了盒子并看到了里面主要的一些东西。<br>
这篇文章也为大家探索Spring MVC开了个好头，它让我们的开发者更进一步地了解了Spring MVC，而不仅仅是单纯地使用它。<br>
Spring MVC还有很多细节、优秀地设计思想以及漂亮地编码风格值得我们去探索和学习。下图是我Google一张关于Spring MVC的流程图，画得很详细，供大家参考。<br>
<img src="https://runcoding.github.io/static/wiki/learn-java/java/spring-mvc/3rd.png" >
 