package com.runcoding;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Spring Boot 应用启动类
 *
 * Created by bysocket on 16/4/26.
 */
// Spring Boot 应用的标识
@SpringBootApplication
// 扫描使用注解方式的servlet
@ServletComponentScan
// mapper 接口类扫描包配置
//注解启动一个服务注册中心提供
//@EnableEurekaServer
//激活Eureka中的DiscoveryClient实现,这样才能实现Controller中对服务信息的输出。
//@EnableDiscoveryClient
public class Application {

    public static void main(String[] args) {
        // 程序启动入口
        // 启动嵌入式的 Tomcat(jetty) 并初始化 Spring 环境及其各 Spring 组件
        new SpringApplicationBuilder(Application.class).web(true).run(args);
        //SpringApplication.run(Application.class,args);
    }
}
