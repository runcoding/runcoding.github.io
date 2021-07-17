package com.runcoding.guava;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.google.common.base.Predicates;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/6/12 16:04
 * @description Guava retryer 异常重试几次
 * Copyright (C), 2017-2018,
 **/
public class GuavaRetryerTest {



    public static void main(String[] args) {

        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.println("--true-");
                String s = null;
                s.length();
                return true;
            }
        };

        Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()
                .retryIfResult(Predicates.<Boolean>isNull())
                .retryIfExceptionOfType(IOException.class)
                .retryIfRuntimeException()
                /**重试3次*/
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();
        try {
            retryer.call(callable);
        } catch (RetryException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }



}
