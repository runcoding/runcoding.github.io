package com.runcoding.service.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/1/2 17:32
 * @description 订单业务支持
 * Copyright (C), 2017-2018,
 **/
@Service
public class TradeSupportService {

    private Logger  logger = LoggerFactory.getLogger(TradeSupportService.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;




}
