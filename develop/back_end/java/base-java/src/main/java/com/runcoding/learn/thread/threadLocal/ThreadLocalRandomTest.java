package com.runcoding.learn.thread.threadLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/14 14:40
 * @description ThreadLocalRandom 随机数
 * Copyright (C), 2017-2018,
 * https://mp.weixin.qq.com/s?__biz=MzU2MTA1OTgyMQ==&mid=2247484093&idx=1&sn=b0fc71ac0991e730f63c19d8cbc3ac4a&scene=19#wechat_redirect
 **/
public class ThreadLocalRandomTest {

    private static Logger  logger = LoggerFactory.getLogger(ThreadLocalRandomTest.class);

    public static void main(String[] args) {
        testRandom();
    }

    /**
     *在单线程情况下每次调用nextInt都是根据老的种子计算出来新的种子，这是可以保证随机数产生的随机性的。
     * Random在多线程下存在竞争种子原子变量更新操作失败后自旋(cas)等待的缺点
     */
    private static void testRandom(){
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int nextInt = random.nextInt(5);
            logger.info("Random int={}",nextInt);
        }
    }

    /**
     * ThreadLocalRandom使用ThreadLocal的原理，让每个线程内持有一个本地的种子变量，
     * 该种子变量只有在使用随机数时候才会被初始化，多线程下计算新种子时候是根据自己线程内维护的种子变量进行更新，从而避免了竞争。
     */
    private static void testThreadLocalRandom(){
        ThreadLocalRandom random =   ThreadLocalRandom.current();
        for (int i = 0; i < 100; i++) {
            int nextInt = random.nextInt(5);
            logger.info("Random int={}",nextInt);
        }
    }

}
