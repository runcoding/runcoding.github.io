package com.midgetontoes.reactorexamples;

import reactor.core.publisher.Flux;

public class Operators {
    public static void main(String[] args) {
        buffer();
        filter();
        window();
        zipWith();
        take();
        reduce();
    }

    private static void buffer() {
        Flux.range(1, 100).buffer(20).subscribe(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
    }

    private static void filter() {
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);
    }

    private static void window() {
        Flux.range(1, 100).window(20).subscribe(System.out::println);
    }

    private static void zipWith() {
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);
        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);
    }

    private static void take() {
        Flux.range(1, 1000).take(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
        Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
        Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
    }

    private static void reduce() {
        Flux.range(1, 100).reduce((x, y) -> x + y).subscribe(System.out::println);
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);
    }


}
