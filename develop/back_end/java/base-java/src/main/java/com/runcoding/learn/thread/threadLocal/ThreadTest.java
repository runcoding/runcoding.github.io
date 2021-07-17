package com.runcoding.learn.thread.threadLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2019-01-31 17:40
 * @description Copyright (C), 2017-2019,
 **/
public class ThreadTest implements Runnable {
    public static Logger  logger = LoggerFactory.getLogger(ThreadTest.class);
    int count  =0;
    @Override
    public void run() {
       while (true){
           int i = count++;
           if(i>=200){
               break;
           }
           logger.info("=="+i);
       }
    }

    public static void main(String[] args) {
        ThreadTest t = new ThreadTest();
        Thread thread1 = new Thread(t);
        Thread thread2 = new Thread(t);
        Thread thread3 = new Thread(t);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
