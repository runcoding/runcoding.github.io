package com.runcoding.learn.concurrent.task;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * http://ifeve.com/%E7%BA%BF%E7%A8%8B%E6%B1%A0%E4%BD%BF%E7%94%A8futuretask%E6%97%B6%E5%80%99%E9%9C%80%E8%A6%81%E6%B3%A8%E6%84%8F%E7%9A%84%E4%B8%80%E7%82%B9%E4%BA%8B/
 */
public class FutureTest {

    /**(1)线程池单个线程，线程池队列元素个数为1
     * 拒绝策略：
     * 1. CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
     * 2. AbortPolicy ：对拒绝任务抛弃处理，并且抛出异常。
     * 3. DiscardPolicy ：对拒绝任务直接无声抛弃，没有异常信息。
     * 4. DiscardOldestPolicy ：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
     * */
    private final static ThreadPoolExecutor executorService = new ThreadPoolExecutor(
            1, 1,
            1L, TimeUnit.MINUTES,
             new ArrayBlockingQueue<>(1),
             /**
              * 在使用DiscardPolicy和DiscardOldestPolicy 都会导致阻塞
              * */
             new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) throws Exception {

        /*添加任务one*/
        Future futureOne = executorService.submit(() -> {
            System.out.println("start runable one");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //(3)添加任务two
        Future futureTwo = executorService.submit(() -> System.out.println("start runable two"));

        //(4)添加任务three
        Future futureThree = null;
        try {
            futureThree = executorService.submit(() -> System.out.println("start runable three"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        //(5)等待任务one执行完毕
        System.out.println("task one " + futureOne.get());
        //(6)等待任务two执行完毕
        System.out.println("task two " + futureTwo.get());

        // (7)等待任务three执行完毕
        System.out.println("task three " + (futureThree == null ? null : futureThree.get()));


        //(8)关闭线程池，阻塞直到所有任务执行完毕
        executorService.shutdown();
    }

}