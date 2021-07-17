package com.midgetontoes.reactorexamples;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ReactorTest {

    private static List<String> words = Arrays.asList(
            "the",
            "quick",
            "brown",
            "fox",
            "jumped",
            "over",
            "the",
            "lazy",
            "dog"
    );

    @Test
    public void simpleCreation() {
        Flux<String> fewWords = Flux.just("Hello", "World");
        Flux<String> manyWords = Flux.fromIterable(words);

        fewWords.subscribe(System.out::println);
        System.out.println();
        manyWords.subscribe(System.out::println);

        /**
         * Flux.interval 用来生成递增的序列，其中第一个 Flux 的时间间隔是100毫秒，第二个 Flux 的时间间隔是10毫秒，并有一秒的延迟。
         * 两个 Flux 表示的流被 merge 合并。
         * bufferTimeout 的设置是最多10个元素和最长500毫秒。
         * 由于生成的流是无限的，我们使用 take(3) 来取前面3个元素。
         * toStream() 是把 Flux 转换成 Java 8 的 Stream ，这样可以阻止主线程退出直到流中全部元素被消费。
         * 在最初的 500 毫秒，只有第一个 Flux 产生数据，因此得到的 List 中只包含5个元素。
         * 在接着的 500 毫秒，由于时间精确度的原因，在 List 中仍然是可能有来自第二个 Flux 的元素。第三个 List 则包含10个元素。
         * */
        Flux.merge(
                Flux.interval(Duration.ofMillis(100)).map(v -> "a" + v),
                Flux.interval(Duration.ofSeconds(1), Duration.ofMillis(10)).map(v -> "b" + v)
        ).bufferTimeout(10, Duration.ofMillis(500))
         .take(3)
         .toStream()
         .forEach(System.out::println);
    }

    @Test
    public void findingMissingLetter() {
        Flux<String> manyLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .sort()
                /**订阅将两个Publisher,并将其合并*/
                .zipWith(Flux.range(1, Integer.MAX_VALUE), (string, count) -> String.format("%2d. %s", count, string));

        manyLetters.subscribe(System.out::println);
    }

    /**延迟订阅*/
    @Test
    public void blocks() {
        Flux<String> helloPauseWorld = Mono.just("Hello")
                .concatWith(Mono.just("world").delaySubscription(Duration.ofMillis(500)))
                .concatWith(Mono.just("Ghost").delaySubscription(Duration.ofMillis(300)));

        helloPauseWorld.toStream().forEach(System.out::println);
    }

    @Test
    public void firstEmitting() {
        Mono<String> a = Mono.just("oops I'm late")
                .delaySubscription(Duration.ofMillis(450));
        Flux<String> b = Flux.just("let's get", "the party", "started")
                .delaySubscription(Duration.ofMillis(400));
        Flux<String> r = Flux.just("Ghost")
                .delaySubscription(Duration.ofMillis(100));
        /**first 从多个竞争数据源中，获取第一个*/
        Flux.first(a,b,r)
            .toIterable()
            .forEach(System.out::println);
    }

    /**预期校验*/
    @Test
    public void stepVerifierTest() {

        StepVerifier.create(Flux.just("foo", "bar"))
                 .expectNext("foo")
                 .expectNext("bar+err")
                 .expectComplete()
                 .verify();

        StepVerifier.create(Flux.just("a", "b"))
                .expectNext("a")
                .expectNext("b")
                .verifyComplete();
    }

    @Test
    public void testWithTime() {
        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2))
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(4))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .verifyComplete();
    }

    @Test
    public void withTestPublisher() {
        final TestPublisher<String> testPublisher = TestPublisher.create();
        testPublisher.next("a");
        testPublisher.next("b");
        testPublisher.complete();

        StepVerifier.create(testPublisher)
                .expectNext("a")
                .expectNext("b")
                .expectComplete();
    }
}
