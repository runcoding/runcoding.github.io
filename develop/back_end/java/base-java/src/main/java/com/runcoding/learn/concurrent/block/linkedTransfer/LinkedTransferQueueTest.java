package com.runcoding.learn.concurrent.block.linkedTransfer;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/1 17:21
 * @description Copyright (C), 2017-2018,
 * https://blog.csdn.net/yjian2008/article/details/16951811
 **/
public class LinkedTransferQueueTest {

    public static void main(String[] args) {
        TransferQueue<String> queue = new LinkedTransferQueue<>();
        Thread producer = new Thread(new Producer(queue));
        //设置为守护进程使得线程执行结束后程序自动结束运行
        producer.setDaemon(true);
        producer.start();
        for (int i = 0; i < 10; i++) {
            Thread consumer = new Thread(new Consumer(queue));
            consumer.setDaemon(true);
            consumer.start();
            try {
                // 消费者进程休眠一秒钟，以便以便生产者获得CPU，从而生产产品
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
