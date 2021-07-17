package com.runcoding.learn.concurrent.task.future;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TaskHandler {

    public void syncHandler(){
        Date start= new Date();
        TaskService taskService= new TaskService();
        taskService.doTask1();
        taskService.doTask2();
        taskService.doTask3();
        System.out.println(String.format("同步执行take time: %ss \r\n",(System.currentTimeMillis()-start.getTime())/1000));
    }

    /**
     * 启用三个线程单独处理3个任务，等处理完成之后，在处理其他事情主线程需要等待，俗称伪异步
     */
    public void asyncHandler(){
        Date start= new Date();
        TaskService taskService= new TaskService();
        ExecutorService executorService= Executors.newFixedThreadPool(3);
        Callable<String>[] callableList= new  Callable[]{()->taskService.doTask1(),()->taskService.doTask2(),()->taskService.doTask3()};
        try {
            List<Future<String>> futures= executorService.invokeAll(Arrays.asList(callableList));
            List<String> results= futures.parallelStream().map(stringFuture -> {
                try {
                    return stringFuture.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }).collect(Collectors.toList());
            System.out.println("results :"+results);
            System.out.println(String.format("异步多线程take time: %ss \r\n",(System.currentTimeMillis()-start.getTime())/1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }

    /**
     * 异步编排不阻塞主线程，当任务执行完之后，通过通知返回结果
     */
    public void completableFuture(){
        Date start= new Date();
        TaskService taskService= new TaskService();
        CompletableFuture<String> completableFuture1= CompletableFuture.supplyAsync(() -> taskService.doTask1());
        CompletableFuture<String> completableFuture2= CompletableFuture.supplyAsync(() -> taskService.doTask2());
        CompletableFuture<String> completableFuture3= CompletableFuture.supplyAsync(() -> taskService.doTask3());
        List<CompletableFuture<String>> futureList= Arrays.asList(completableFuture1,completableFuture2,completableFuture3);
        CompletableFuture<Void> doneFuture= CompletableFuture.allOf(completableFuture1,completableFuture2,completableFuture3);
        try {
            doneFuture.whenComplete((aVoid, throwable) -> {
                List results = futureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
                System.out.println("results :" + results);
            });
            System.out.println(String.format("异步编排take time: %ss ",(System.currentTimeMillis()-start.getTime())/1000));
            Thread.sleep(4000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
       // TaskHandler handler= new TaskHandler();
       // handler.completableFuture();
         AtomicInteger i = new AtomicInteger(0);
        for(;;){
            new Thread(new Runnable(){
                @Override
                public void run(){
                    System.out.println(i.incrementAndGet());
                    try {
                        Thread.sleep(10000l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
