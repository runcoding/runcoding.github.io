package com.runcoding.learn.method.handle;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author runcoding
 * @date 2019-08-21
 * @desc: https://blog.csdn.net/aesop_wubo/article/details/48858931
 * java7在JSR 292中增加了对动态类型语言的支持，使java也可以像C语言那样将方法作为参数传递，其实现在lava.lang.invoke包中。MethodHandle作用类似于反射中的Method类，但它比Method类要更加灵活和轻量级。通过MethodHandle进行方法调用一般需要以下几步：
 *
 * （1）创建MethodType对象，指定方法的签名；
 * （2）在MethodHandles.Lookup中查找类型为MethodType的MethodHandle；
 * （3）传入方法参数并调用MethodHandle.invoke或者MethodHandle.invokeExact方法。
 */
@Slf4j
public class MethodHandleTest {

    @Test
    public  void staticMethod() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        /**获取静态的代理方法*/
        MethodHandle mh = lookup.findStatic(
                                            /**指定被代理的类*/
                                            MethodHandleTest.class,
                                            /**指定被代理的方法*/
                                            "doubleValFunc",
                                            /**指定被代理的方法参数列表*/
                                            MethodType.methodType(int.class, int.class));

        List<Integer> dataList = Arrays.asList(1, 2, 3, 4, 5);
        // 方法做为参数
        MethodHandleTest.transform(dataList, mh);
        log.info("dataList={}",dataList);
    }

    public static List<Integer> transform(List<Integer> dataList, MethodHandle handle) throws Throwable {
        for (int i = 0; i < dataList.size(); i++) {
            //调用代理方法invoke
            dataList.set(i, (Integer) handle.invoke(dataList.get(i)));
        }
        return dataList;
    }

    /**被代理的方法*/
    public static int doubleValFunc(int val) {
        return val * 2;
    }



}
