package com.runcoding.gateway.controller;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2019-03-24 14:33
 * @description Copyright (C), 2017-2019,
 **/
@RestController
public class RouteController {

    @GetMapping("/java")
    public String  java(){
        System.out.println("req java");
        return "java";
    }

    @GetMapping("/mysql")
    public String  mysql(){
        System.out.println("req mysql");
        return "mysql";
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .build();
    }

}


