package com.runcoding.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.runcoding.service.DynamicServerProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("config")
@RefreshScope
public class ApplicationController {

    @Autowired
    private DynamicServerProcessor dynamicServerProcessor;

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }

    @RequestMapping(value = "/getServerHostPorts", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> get(@RequestParam String serviceName) throws NacosException {
        Set<String> serverHostPorts = dynamicServerProcessor.getServerHostPorts();
        return serverHostPorts;
    }

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    public String echo(@PathVariable String string) {
        return "Hello Nacos Discovery " + string;
    }

}
