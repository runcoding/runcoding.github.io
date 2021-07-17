package com.runcoding.service.order.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.runcoding.dao.order.OrderMapper;
import com.runcoding.model.po.order.OrderPo;
import com.runcoding.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author runcoding
 * @Date 2018-01-02 17:23:57
 */
@Service
public class OrderServiceImpl implements OrderService {

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public List<OrderPo> all() {
        PageHelper.startPage(1,2);
        Page<OrderPo> page = orderMapper.all();
        return page.getResult();
    }

    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    @Override
    public OrderPo select(Long id) {
        return orderMapper.select(id);
    }

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    @Override
    @Transactional()
    public int insert(OrderPo po) {
        return orderMapper.insert(po);
    }

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    @Override
    public int update(OrderPo po) {
        return orderMapper.update(po);
    }

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    @Override
    public int delete(OrderPo po) {
        return orderMapper.delete(po);
    }
}