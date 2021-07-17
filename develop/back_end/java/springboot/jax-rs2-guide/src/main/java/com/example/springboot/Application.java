package com.example.springboot;

import com.example.springboot.domain.Hello;
import com.example.springboot.web.HelloResource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:jersey-project.properties")
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new Application().configure(
                new SpringApplicationBuilder(Application.class)).run(args);
        HelloResource helloResource = context.getBean(HelloResource.class);
        Hello ok = helloResource.ok();
        System.out.println(ok);
    }
}
