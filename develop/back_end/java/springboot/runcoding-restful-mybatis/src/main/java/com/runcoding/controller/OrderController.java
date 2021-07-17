package com.runcoding.controller;

import com.alibaba.fastjson.JSON;
import com.runcoding.dao.account.AccountMapper;
import com.runcoding.model.po.account.AccountPo;
import com.runcoding.model.po.order.OrderPo;
import com.runcoding.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: runcoding
 * @email: runcoding@163.com
 * @created Time: 2018/1/2 17:42
 * @description 订单服务
 **/
@RestController()
public class OrderController {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DataSource dataSource;



    @PostMapping(value = "/get")
    @ResponseBody
    public OrderPo  getOrder(){
        try {
            String sql = "SELECT id AS id FROM `order` WHERE id=?";
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, 1);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("resultSet="+resultSet.getStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        OrderPo orderPo    = orderService.select(1L);
       // List<OrderPo> list = orderService.all();
       // AccountPo account  = accountMapper.select(1L);
        return null;
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public OrderPo createOrder(@RequestBody OrderPo order){
        orderService.insert(order);
        return order;
    }

}
