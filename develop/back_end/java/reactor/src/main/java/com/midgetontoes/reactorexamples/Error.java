package com.midgetontoes.reactorexamples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Error {
    public static void main(String[] args) {
        subscribe();
        onErrorReturn();
        retry();
    }

    private static void subscribe() {
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);
    }

    private static void onErrorReturn() {
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);
    }



    private static void retry() {
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(1)
                .subscribe(System.out::println);
    }
}
