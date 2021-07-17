package com.runcoding.learn.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/1 15:24
 * @description 可重入锁(同一个线程可多次获取同一把锁)
 * https://blog.csdn.net/lipeng_bigdata/article/details/52154637
 **/
public class ReentrantLockTest {

    private ReentrantLock nonfairLock = new ReentrantLock(false);

    /**不公平锁
     * 非公平锁上来就无视等待队列的存在而抢占锁，通过基于CAS操作的compareAndSetState(0, 1)方法，
     * 试图修改当前锁的状态，这个0表示AbstractQueuedSynchronizer内部的一种状态，
     * 针对互斥锁则是尚未有线程持有该锁，而>=1则表示存在线程持有该锁，并重入对应次数，
     * 这个上来就CAS的操作也是非公共锁的一种体现，CAS操作成功的话，则将当前线程设置为该锁的唯一拥有者。
     抢占不成功的话，则调用父类的acquire()方法，按照上面讲的，继而会调用tryAcquire()方法，这个方法也是由最终实现类NonfairSync实现的
     * */
    public  void nonfairSync() throws InterruptedException {


        /**获取锁(如果获取不成功一直等待到获取锁)*/
        nonfairLock.lock();
        /**获取锁(获取到锁返回true,否则直接返回false)*/
        boolean status = nonfairLock.tryLock();
        /**获取锁(如果直接获取到锁返回true，否则休眠500毫秒内未获取到锁抛出线程中断)*/
        status = nonfairLock.tryLock(500L, TimeUnit.MILLISECONDS);
        /**获取锁(如果直接获取到锁返回true，否则休眠知道获取或被中断)*/
        nonfairLock.lockInterruptibly();
        try {
            /**正常业务代码*/
            System.out.println("正常业务代码");

        } finally {
            /**释放锁*/
            nonfairLock.unlock();
        }
    }

    public static void main(String[] args) {
        try {
            new ReentrantLockTest().nonfairSync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
