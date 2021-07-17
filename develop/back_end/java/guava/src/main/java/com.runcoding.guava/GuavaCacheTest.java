package com.runcoding.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.sql.Time;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/6/12 20:05
 * @description Copyright (C), 2017-2018,
 **/
public class GuavaCacheTest {

    /***
     * 基于引用的移除：
     * 　　这种移除方式主要是基于java的垃圾回收机制，根据键或者值的引用关系决定移除
     * 　　主动移除数据方式，主动移除有三种方法：
     * 　　1.单独移除用 Cache.invalidate(key)
     * 　　2.批量移除用 Cache.invalidateAll(keys)
     * 　　3.移除所有用 Cache.invalidateAll()
     */
    static Cache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(1000).
            //这个方法是根据某个键值对最后一次访问之后多少时间后移除
            expireAfterAccess(60, TimeUnit.SECONDS).
            //这个方法是根据某个键值对被创建或值被替换后多少时间移除
            expireAfterWrite(60,TimeUnit.SECONDS).build();

    public static void main(String[] args) throws ExecutionException {
    /*    cache.put("name","runcoding");
        System.out.println(cache.getIfPresent("name"));
*/
        Object name = cache.get("name", new Callable() {
            @Override
            public Object call() throws Exception {
                return "runcoding1";
            }
        });

        System.out.println(name);

    }

}
