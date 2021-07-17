package com.runcoding.learn.concurrent.block;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/1 14:59
 * @description ArrayBlockingQueue 使用
 * ArrayBlockingQueue是一个用数组实现的有界阻塞队列。
 * 此队列按照先进先出（FIFO）的原则对元素进行排序。
    默认情况下不保证访问者公平的访问队列，
    所谓公平访问队列是指阻塞的所有生产者线程或消费者线程，
    当队列可用时，可以按照阻塞的先后顺序访问队列，
    即先阻塞的生产者线程，可以先往队列里插入元素，先阻塞的消费者线程，
    可以先从队列里获取元素。通常情况下为了保证公平性会降低吞吐量。
 数据结构：数组
 **/

public class ArrayBlockingQueueTest {

    private static Logger  logger  = LoggerFactory.getLogger(ArrayBlockingQueueTest.class);

    public static void main(String[] args) {

        ArrayBlockingQueue queue = new ArrayBlockingQueue(2,true);
        /**往队列中添加元素(通过offer()实现，超出容器大小抛错)*/
        boolean status = queue.add("capacity1");
        /**往队列中添加元素(如果超出容器大小限制，则返回false)*/
        boolean capacity2 = queue.offer("capacity2");
        /**返回将要出队列的元素*/
        Object peek = queue.peek();
        /**dequeue 出队列，返回当前出队列的对象*/
        Object poll = queue.poll();
        logger.info("poll="+poll);
        poll = queue.poll();
        logger.info("poll="+poll);
    }


}
