package com.runcoding.learn.concurrent.block;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/1 16:44
 * @description 是一个用链表实现的有界阻塞队列。此队列的默认和最大长度为Integer.MAX_VALUE。此队列按照先进先出的原则对元素进行排序
 * 数据结构：链表(记录上一个和下一个节点node)
 * Copyright (C), 2017-2018,
 **/
public class LinkedBlockingQueueTest {

    public static void main(String[] args) {
        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        queue.offer("1");
        queue.offer("2");
        System.out.println(queue.poll());
    }
}
