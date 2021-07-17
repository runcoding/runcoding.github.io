package com.runcoding.controller;

import com.runcoding.service.translation.AlphaCoding;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class ApplicationController {

    @Autowired
    private AlphaCoding alphaCoding;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value="进入系统应用主页", notes="默认跳转到swagger-ui")
    public String app(){
        return "redirect:/swagger-ui.html";
    }

    @RequestMapping(value = "/alphaCoding",method = RequestMethod.GET)
    public String alphaCoding(){
        try {
            alphaCoding.alphaByWord();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "s";
    }



}

