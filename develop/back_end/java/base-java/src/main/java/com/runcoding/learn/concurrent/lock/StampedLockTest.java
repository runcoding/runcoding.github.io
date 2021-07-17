package com.runcoding.learn.concurrent.lock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/4/14 15:16
 * @description S
 * tampedLock作为JAVA8中出现的新型锁，很可能在大多数场景都可以替代ReentrantReadWriteLock。
 * 它对于读/写都提供了四个接口(换成write为写锁)：
 * readLock()                             获取读锁（阻塞，不响应中断）
 * tryReadLock()                          获取读锁（立即）
 * tryReadLock(long time, TimeUnit unit)  限时获取读锁（响应中断）
 * readLockInterruptibly()                获取读锁（阻塞，响应中断）
 * http://ifeve.com/stampedlock-bug-cpu/
 **/
public class StampedLockTest {

    public static void main(String[] args) throws InterruptedException {
        final StampedLock lock = new StampedLock();
        new Thread(() -> {
            long readLong = lock.writeLock();
            LockSupport.parkNanos(6100000000L);
            lock.unlockWrite(readLong);
        }).start();
        Thread.sleep(100);
        for( int i = 0; i < 3; ++i) {
            new Thread(new OccupiedCPUReadThread(lock)).start();
        }
    }

    private static class OccupiedCPUReadThread implements Runnable{
        private StampedLock lock;
        public OccupiedCPUReadThread(StampedLock lock){
            this.lock = lock;
        }
        @Override
        public void run(){
            Thread.currentThread().interrupt();
            long lockr = lock.readLock();
            System.out.println(Thread.currentThread().getName() + " get read lock");
            lock.unlockRead(lockr);
        }
    }

}
