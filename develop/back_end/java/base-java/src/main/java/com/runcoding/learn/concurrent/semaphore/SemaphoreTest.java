package com.runcoding.learn.concurrent.semaphore;

import java.util.concurrent.*;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/5/6 12:46
 * @description 信号量
 * Copyright (C), 2017-2018,
 **/
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService exec =   new ThreadPoolExecutor(
                3, 3,
                1L, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(3),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            exec.execute(new Task(semaphore));
        }
        exec.shutdown();
    }

    public static class Task implements Runnable {

        private Semaphore semaphore;

        public Task(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + "获取了资源");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "准备释放资源");
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + "释放了资源");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
