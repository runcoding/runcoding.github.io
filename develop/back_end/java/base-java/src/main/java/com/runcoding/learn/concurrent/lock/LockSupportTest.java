package com.runcoding.learn.concurrent.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/14 15:33
 * @description LockSupport是JDK中比较底层的类，用来创建锁和其他同步工具类的基本线程阻塞原语。
 * java锁和同步器框架的核心 AQS: AbstractQueuedSynchronizer，
 * 就是通过调用 LockSupport .park()和 LockSupport .unpark()实现线程的阻塞和唤醒 的。
 * LockSupport 很类似于二元信号量(只有1个许可证可供使用)，如果这个许可还没有被占用，当前线程获取许可并继 续 执行；如果许可已经被占用，当前线 程阻塞，等待获取许可。
 * Copyright (C), 2017-2018,
 **/
public class LockSupportTest {

    public static void main(String[] args) {

        //获取当前线程
        final Thread currentThread = Thread.currentThread();

        Runnable runnable = () -> {
            try {
                //睡眠2秒，等待主线程调用park
                Thread.sleep(2000);
                System.out.println("子线程进行unpark操作！");
                // 进行唤醒给定的currentThread线程
                LockSupport.unpark(currentThread);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };

        new Thread(runnable).start();
        System.out.println("开始阻塞！");
        // 进行阻塞给定的currentThread线程
        LockSupport.park(currentThread);
        System.out.println("结束阻塞！");
    }


}
