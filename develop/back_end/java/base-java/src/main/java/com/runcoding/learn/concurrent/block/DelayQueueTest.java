package com.runcoding.learn.concurrent.block;


import java.text.DateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/1 17:02
 * @description 队列中的元素必须实现Delayed接口，在创建元素时可以指定多久才能从队列中获取当前元素。
 * 只有在延迟期满时才能从队列中提取元素
 * Copyright (C), 2017-2018,
 **/
public class DelayQueueTest {



    public static void main(String[] args) throws InterruptedException {
            DelayQueue<Student> queue = new DelayQueue<>();
            queue.add(new Student("范冰冰"));
            queue.add(new Student("成  龙"));
            queue.add(new Student("submit", Times.SUBMIT_TIME.getValue(), TimeUnit.SECONDS));
            while (true) {
                // 必要时进行阻塞等待
                Student s = queue.take();
                if (s.getName().equals("submit")) {
                    System.out.println("时间已到，全部交卷！");
                    // 利用Java8 Stream使尚未交卷学生交卷
                    queue.parallelStream()
                            .filter(v -> v.getExpire() >= s.getExpire())
                            .map(Student::submit)
                            .forEach(System.out::println);
                    System.exit(0);
                }
                System.out.println(s);
            }
        }

    }



    enum Times {
        SUBMIT_TIME(10), SUMBMIT_LIMIT(2), MAX_RAND_TIME(15);
        private final int value;

        private Times(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
     }

     class Student implements Delayed {
        private String name;
        // 考试花费时间，单位为毫秒
        private long delay;
        // 交卷时间，单位为毫秒
        private long expire;

        // 此构造可随机生成考试花费时间
        public Student(String name) {
            this.name = name;
            // 随机生成考试花费时间
            this.delay = TimeUnit.MILLISECONDS.convert(getRandomSeconds(), TimeUnit.SECONDS);
            this.expire = System.currentTimeMillis() + this.delay;
        }

        // 此构造可指定考试花费时间
        public Student(String name, long delay, TimeUnit unit) {
            this.name = name;
            this.delay = TimeUnit.MILLISECONDS.convert(delay, unit);
            this.expire = System.currentTimeMillis() + this.delay;
        }

         // 获取随机花费时间
        public int getRandomSeconds() {
            return new Random().nextInt(Times.MAX_RAND_TIME.getValue() -Times.SUMBMIT_LIMIT.getValue())
                    + Times.SUMBMIT_LIMIT.getValue();
        }

        // 设置花费时间和交卷时间，考试时间结束强制交卷时调用此方法
        public Student submit() {
            setDelay(Times.SUBMIT_TIME.getValue(), TimeUnit.SECONDS);
            setExpire(System.currentTimeMillis());
            return this;
        }

        public String getName() {
            return name;
        }

        public long getExpire() {
            return expire;
        }

        public void setDelay(long delay, TimeUnit unit) {
            this.delay = TimeUnit.MILLISECONDS.convert(delay, TimeUnit.SECONDS);
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }

        // 此方法的实现用于定义优先级
        @Override
        public int compareTo(Delayed o) {
            long td = this.getDelay(TimeUnit.MILLISECONDS);
            long od = o.getDelay(TimeUnit.MILLISECONDS);
            return td > od ? 1 : td == od ? 0 : -1;
        }

        // 这里返回的是剩余延时，当延时为0时，此元素延时期满，可从take()取出
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public String toString() {
            return "学生姓名：" + this.name + ",考试用时：" + TimeUnit.SECONDS.convert(delay, TimeUnit.MILLISECONDS) + ",交卷时间："
                    + DateFormat.getDateTimeInstance().format(new Date(this.expire));
        }


}
