package com.runcoding.aviator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.Options;
import com.runcoding.aviator.bean.AviatorBean;
import com.runcoding.aviator.function.AddFunction;
import com.runcoding.aviator.function.FreeFunctionLoader;
import com.runcoding.aviator.function.GetFirstNonNullFunction;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * @author runcoding
 * @date 2019-08-20
 * @desc: aviator 测试
 * https://github.com/killme2008/aviator/wiki#%E7%AE%97%E6%9C%AF%E8%BF%90%E7%AE%97%E7%AC%A6
 */
@Slf4j
public class AviatorDemo {

    /** Aviator的数值类型仅支持Long和Double, 任何整数都将转换成Long, 任何浮点数都将转换为Double,  * */
    @Test
    public void  testMathematics(){
        Long l = (Long) AviatorEvaluator.execute("1+2+3");
        log.info("Long,计算1+2+3={}",l);

        Double d = (double) AviatorEvaluator.execute("1+2+3.3");
        log.info("Double,计算1+2+3.3={}",d);

        BigDecimal price = (BigDecimal) AviatorEvaluator.execute("10.025M * 2");
        log.info("BigDecimal,计算10.025 * 2 ={}",price);

        BigInteger bigInteger = (BigInteger) AviatorEvaluator.execute("92233720368547758074+1000");
        log.info("bigInteger,计算92233720368547758074+1000 ={}",bigInteger);
        /**默认计算精度为MathContext.DECIMAL128*/
        AviatorEvaluator.setOption(Options.MATH_CONTEXT, MathContext.DECIMAL64);

         /**三元操作符*/
        log.info("三元操作符",AviatorEvaluator.exec("a>0? 'yes':'no'", 1));

    }

    /**
     * 如果开启了 ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL 选项，
     * 那么在表达式中出现的浮点数都将解析为 BigDecimal，
     * 这是为了方便一些用户要求高精度的计算，又不想额外地给浮点数加上 M 后缀标记为 BigDecimal：
     */
    @Test
    public void testAlwaysUseDoubleAsDecimal() {
        AviatorEvaluator.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        Object val = AviatorEvaluator.execute("3.2");
        assertTrue(val instanceof BigDecimal);

        val = AviatorEvaluator.execute("20.00 / 6 ");
        log.info("20.00 / 6 = {}", val);
        log.info("20.00 / 6 = {}", ((BigDecimal)val).setScale(2, RoundingMode.DOWN));
    }

    @Test
    public void testString(){
        String yourName = "Running";
        Map<String, Object> env = new HashMap<>(2);
        env.put("yourName", yourName);
        String result = (String) AviatorEvaluator.execute(" 'hello ' + yourName ", env);
        log.info("字符串替换,result={}",result);
    }

    /** 支持内置函数 */
    @Test
    public void testFunction(){
        Long length = (Long) AviatorEvaluator.execute("string.length('Running')");
        log.info("内置函数 string.length('xx')={}",length);

        Boolean contains = (Boolean) AviatorEvaluator.execute("string.contains(\"test\", string.substring('hello', 1, 2))");
        log.info("内置函数 string.contains('xx')={}",contains);
    }

    /**自定义函数*/
    @Test
    public void testAutoAddFunction() {
        /**自定义两个参数的函数*/
        AviatorEvaluator.addFunction(new AddFunction());
        log.info("执行自定义函数运算={}",AviatorEvaluator.execute("addFunc(1, 2)"));
        log.info("执行自定义函数运算={}",AviatorEvaluator.execute("addFunc(addFunc(1, 2), 100)"));
        AviatorEvaluator.removeFunction("addFunc");

        AviatorEvaluator.addFunctionLoader(new FreeFunctionLoader());
        log.info("通过自定义价值器加载，执行自定义函数运算={}",AviatorEvaluator.execute("addFunc(1, 2)"));


        /**自定义多个参数的函数*/
        AviatorEvaluator.addFunction(new GetFirstNonNullFunction());
        log.info("执行自定义函数运算getFirstNonNull(1)={}",AviatorEvaluator.execute("getFirstNonNull(1)"));
        log.info("执行自定义函数运算getFirstNonNull(3,4)={}",AviatorEvaluator.execute("getFirstNonNull(3,4)"));
        AviatorEvaluator.removeFunction("getFirstNonNull");


        /**自定义lambda的函数*/
        AviatorEvaluator.defineFunction("addByLambdaFunc", "lambda (x,y) -> x + y end");
        log.info("执行lambda函数运算addByLambdaFunc(3,7)={}",AviatorEvaluator.execute("addByLambdaFunc(3,7)"));

    }

    /** lambda 表达式  */
    @Test
    public void testLambda(){
        Long lambdaVal = (Long) AviatorEvaluator.exec("(lambda (x,y) -> x + y end)(x,y)", 1, 2);
        log.info("lambda运行 (lambda (x,y) -> x + y end)={}",lambdaVal);

        lambdaVal =  (Long) AviatorEvaluator .exec("(lambda (x) -> lambda(y) -> lambda(z) -> x + y + z end end end)(1)(2)(3)");
        log.info("lambda运行 (lambda (x) -> lambda(y) -> lambda(z) -> x + y + z end end end)={}",lambdaVal);
    }

    /**预编译*/
    @Test
    public void testCompile(){
        String expression = "a-(b-c)>100";
        /**编译表达式, 会将表达式缓存在ConcurrentHashMap*/
        Expression compiledExp = AviatorEvaluator.compile(expression,true);
        Map<String, Object> env = new HashMap<>(8);
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        log.info("执行={}",result);
        /**作废缓存中的表达式*/
        AviatorEvaluator.invalidateCache(expression);
    }

    @Test
    public void testCollection(){
        Map<String, Object> env = new HashMap<>(8);
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add(" world");

        env.put("list", list);
        log.info("list[0]+list[1]={}",AviatorEvaluator.execute("list[0]+list[1]", env));

        int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;
        env.put("array", array);

        log.info("{}",AviatorEvaluator.execute("'array[0]+array[1]+array[2]=' + (array[0]+array[1]+array[2])", env));

        Map<String, Date> map = new HashMap<>(8);
        map.put("date", new Date());
        env.put("mmap", map);

        log.info("{}",AviatorEvaluator.execute("'today is ' + mmap.date ", env));

        log.info("split={}",AviatorEvaluator.exec("string.split(s,',')[0]", "coding,java"));
    }

    /**
     * Aviator 会自动将匹配成功的捕获分组(capturing groups) 放入 env ${num}的变量中,
     * 其中$0 指代整个匹配的字符串,而$1表示第一个分组，$2表示第二个分组以此类推。
     * 请注意，分组捕获放入 env 是默认开启的，因此如果传入的 env 不是线程安全并且被并发使用，可能存在线程安全的隐患。关闭分组匹配，
     * 可以通过 AviatorEvaluator.setOption(Options.PUT_CAPTURING_GROUPS_INTO_ENV, false); 来关闭，对性能有稍许好处。
     */
    @Test
    public void testRegex(){
        String email = "runcoding@163.com";
        Map<String, Object> env = new HashMap<>(2);
        env.put("email", email);
        String username = (String) AviatorEvaluator.execute("email=~/([\\w0-8]+)@\\w+[\\.\\w+]+/ ? $1 : 'unknow' ", env);
        log.info("regex username = {}",username);

    }

    /**
     * 支持java bean 方式获取，但是是通过commons-beanutils的方式方式,性能并不高
     *  commons-beanutils还支持类似 #map.array.[0].name这样的访问语法，如果不满足JavaBean规范的，请尝试使用这样的语法做嵌套访问。
     * */
    @Test
    public void testBean(){
        AviatorBean runcoding = AviatorBean.builder().id(26).username("runcoding").build();
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("runcoding", runcoding);
        log.info("{}",AviatorEvaluator.execute("'id = '+runcoding.id", env));
        log.info("{}",AviatorEvaluator.execute("'username = '+#runcoding.username", env));
    }

    /**
     * 内置函数 https://github.com/killme2008/aviator/wiki/%E5%86%85%E7%BD%AE%E5%87%BD%E6%95%B0
     * */
    @Test
    public void testSeq(){
        Map<String, Object> env = Maps.newHashMap();
        ArrayList<Integer> list = Lists.newArrayList(3,20,10);
        env.put("list", list);
        log.info("求长度:{}",AviatorEvaluator.execute("count(list)", env));
        log.info("聚合={}",AviatorEvaluator.execute("reduce(list,+,0)", env));
        log.info("过滤出list中所有大于9的元素并返回集合={}", AviatorEvaluator.execute("filter(list,seq.gt(9))", env));
        log.info("判断元素在不在集合里={}",AviatorEvaluator.execute("include(list,10)", env));
        log.info("排序={}",AviatorEvaluator.execute("sort(list)", env));

        /**遍历整个集合,map接受的第二个函数将作用于集合中的每个元素,这里简单地调用println打印每个元素*/
        AviatorEvaluator.execute("map(list,println)", env);
    }


    @Test
    public  void option() throws FileNotFoundException {
        /** 默认以执行速度优先 = AviatorEvaluator.EVAL , (编译优先 = AviatorEvaluator.COMPILE )*/
       // AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.COMPILE);

        /**每个表达式生成的字节码*/
        AviatorEvaluator.setOption(Options.TRACE, true);
        //System.setProperty("aviator.asm.trace","true");
         AviatorEvaluator.compile("1+2+3",true);
    }

}
