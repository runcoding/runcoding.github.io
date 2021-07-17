package com.reactor.runningghost.web.controller;

import com.reactor.runningghost.web.model.MyReactiveLibrary;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class ExampleController {

   private final MyReactiveLibrary reactiveLibrary = new MyReactiveLibrary();


   @GetMapping("hello/{who}")
   public Mono<String> hello(@PathVariable String who) {
      return Mono.just(who)
                 .map(w -> "Hello " + w + "!");
   }

   @GetMapping("helloDelay/{who}")
   public Mono<String> helloDelay(@PathVariable String who) {
      return reactiveLibrary.withDelay("Hello " + who + "!!", 2);
   }


}