package com.runcoding.learn.concurrent.task.future;

public class TaskService {
    public String doTask1(){
        try {
            Thread.sleep(1000L);
            System.out.println("do task 1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "do task 1 success.";
    }
    public String doTask2(){
        try {
            Thread.sleep(2000L);
            System.out.println("do task 2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "do task 2 success.";
    }
    public String doTask3(){
        try {
            Thread.sleep(3000L);
            System.out.println("do task 3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "do task 3 success.";
    }
}
