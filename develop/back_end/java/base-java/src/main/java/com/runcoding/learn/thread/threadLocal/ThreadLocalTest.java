package com.runcoding.learn.thread.threadLocal;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/14 13:59
 * @description ThreadLocal 测试
 * https://mp.weixin.qq.com/s?__biz=MzU2MTA1OTgyMQ==&mid=2247484132&idx=1&sn=6ccba4564b43a18b0c182c57dbed6f40&scene=19#wechat_redirect
 **/
public class ThreadLocalTest {

    static class  LocalVariable{
        private Long[] a  = new Long[1024*1024];
    }

    final static ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,
                                    1,TimeUnit.MINUTES,new LinkedBlockingDeque<>());

    final static  ThreadLocal<LocalVariable> localVariable = new ThreadLocal<>();

    /**
     * 线程池中使用ThreadLocal导致的内存泄露
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        new Thread();
        for (int i = 0; i < 50; i++) {
            poolExecutor.execute(() -> {
                localVariable.set(new LocalVariable());
                System.out.println("use local variable");
                //localVariable.remove();
            });
            Thread.sleep(1000);
        }

        Future<?> future = poolExecutor.submit(() -> {
            System.out.println("future");
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        /**暂停等待*/
        future.cancel(true);
    }

}
