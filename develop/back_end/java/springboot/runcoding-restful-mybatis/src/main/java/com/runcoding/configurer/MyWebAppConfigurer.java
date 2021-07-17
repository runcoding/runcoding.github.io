package com.runcoding.configurer;

import com.runcoding.handler.interceptors.AppInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        registry.addInterceptor(new AppInterceptor()).addPathPatterns("/**");
       // registry.addInterceptor(new AppInterceptor2()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}