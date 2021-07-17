package com.runcoding.rxjava2;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lombok.NonNull;
import org.junit.Test;
import sun.jvm.hotspot.runtime.Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HelloWorld {

    @Test
    public  void flowableJust() {
        Flowable.just("Hello world").subscribe(System.out::println);

        //将发送整数10，11，12，13，14
        Observable.range(10, 5).subscribe(System.out::println);

        Observable.just("Hello, world!")
                .map(s -> s.hashCode())
                .map(i -> Integer.toString(i))
                .subscribe(s -> System.out.println(s));

    }

    /**遍历list*/
    @Test
    public  void observableList() {

        //每隔一秒发送一次
        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);
        interval.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                System.out.println("aLong="+aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        List<String> list = new ArrayList<>();
        list.add("from1");
        list.add("from2");
        list.add("from3");
        Observable<String> stringObservable = Observable.fromIterable(list);
        stringObservable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("String="+s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Test
    public  void subscribeDispose() {

        // 第一步：初始化Observable
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            System.out.println( "Observable emit 1" + "\n");
            e.onNext(1);
            System.out.println( "Observable emit 2" + "\n");
            e.onNext(2);
            System.out.println( "Observable emit 3" + "\n");
            e.onNext(3);
            e.onComplete();
            System.out.println( "Observable emit 4" + "\n" );
            e.onNext(4);
        }).subscribe(new Observer<Integer>() {
            // 第三步：订阅

            // 第二步：初始化Observer
            private int i;

            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("integer="+integer);
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                   // mDisposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println( "onError : value : " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                System.out.println( "onComplete" + "\n" );
            }
        });
    }


}