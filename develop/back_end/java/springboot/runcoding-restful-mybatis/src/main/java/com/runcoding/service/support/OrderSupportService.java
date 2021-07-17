package com.runcoding.service.support;

import com.alibaba.fastjson.JSON;
import com.runcoding.model.po.account.AccountPo;
import com.runcoding.model.po.order.OrderPo;
import com.runcoding.service.account.AccountService;
import com.runcoding.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/1/2 17:32
 * @description 订单业务支持
 * Copyright (C), 2017-2018,
 **/
@Service
public class OrderSupportService {

    private Logger  logger = LoggerFactory.getLogger(OrderSupportService.class);

    @Autowired
    private AccountService accountService;


    @Autowired
    private OrderService orderService;

   @Transactional(rollbackFor = Exception.class,isolation = Isolation.READ_UNCOMMITTED)
    public  void createOrder(OrderPo orderPo){
        AccountPo account = AccountPo.builder().username("run").build();
        int insertAccount = accountService.insert(account);
        orderPo.setUserId(account.getId());
        int insertOrder = orderService.insert(orderPo);
        logger.info("insertOrder="+insertOrder);
        logger.info("order0="+JSON.toJSONString(orderService.select(2L)));
        orderPo.setId(2L);
        String i = null;
        //i.length();
        orderPo.setOrderNumber(UUID.randomUUID().toString());
        orderService.update(orderPo);
        logger.info("order1="+JSON.toJSONString(orderPo));
        logger.info("order2="+JSON.toJSONString(orderService.select(2L)));

    }


}
