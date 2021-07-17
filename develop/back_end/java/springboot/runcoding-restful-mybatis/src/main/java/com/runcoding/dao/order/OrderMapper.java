package com.runcoding.dao.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.pagehelper.Page;
import com.runcoding.model.po.order.OrderPo;


/**
 * @author runcoding
 * @Date 2018-01-02 17:23:57
 */
public interface OrderMapper {

    @JSONField(name = "all")
    Page<OrderPo> all();
    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    OrderPo select(Long id);

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    int insert(OrderPo po);

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    int update(OrderPo po);

    
    /**
     * @author runcoding
     * @Date 2018-01-02 17:23:57
     */
    int delete(OrderPo po);


}