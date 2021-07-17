package com.runcoding.sso.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Component
public class ExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String jsonErrorHandler(Exception e) {
        logger.error("出错了：",e);
        if(e instanceof AccessDeniedException){
           return "权限不足";
        }
        return e.getMessage();
    }

}
