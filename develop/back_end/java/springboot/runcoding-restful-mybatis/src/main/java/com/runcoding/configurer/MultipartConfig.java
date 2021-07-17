package com.runcoding.configurer;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/***
 * springboot里默认使用tomcat的上传文件大小限制，即1MB
 */
@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("2MB");
        factory.setMaxRequestSize("2MB");
        return factory.createMultipartConfig();
    }

}