package com.runcoding.learn.java8.lambda.worker;

@FunctionalInterface
public interface WorkerInterface {

    void doWork();

    /**不能存在两个方法*/
    //void doMoreWork();
}
