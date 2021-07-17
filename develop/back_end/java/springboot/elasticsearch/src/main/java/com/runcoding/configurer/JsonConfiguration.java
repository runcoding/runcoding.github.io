package com.runcoding.configurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JsonConfiguration {


    /**
     * 序列化配置
     * @return
     */
    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converter.setFeatures(
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteDateUseDateFormat
        );
        return converter;
    }

}
