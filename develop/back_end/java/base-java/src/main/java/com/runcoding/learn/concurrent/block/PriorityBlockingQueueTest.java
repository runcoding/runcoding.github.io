package com.runcoding.learn.concurrent.block;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/1 16:49
 * @description PriorityBlockingQueue是一个支持优先级的无界队列。默认情况下元素采取自然顺序排列，
 * 也可以通过比较器comparator来指定元素的排序规则。元素按照升序排列。
 * 数据结构：数组
 * Copyright (C), 2017-2018,
 **/
public class PriorityBlockingQueueTest {

    public static void main(String[] args) {
        PriorityBlockingQueue<User> queue = new PriorityBlockingQueue<>(2);
        queue.add(new User(1,"xu"));
        queue.add(new User(30,"run"));
        for (User u : queue){
            System.out.println(u.age);
        }

    }

    static class User implements Comparable<User>{

        public User(int age,String name) {
            this.age = age;
            this.name = name;
        }

        int age;
        String name;

        @Override
        public int compareTo(User o) {
            return this.age > o.age ? -1 : 1;
        }
    }
}
