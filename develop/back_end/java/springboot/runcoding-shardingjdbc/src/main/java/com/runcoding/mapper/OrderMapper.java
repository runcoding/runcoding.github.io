
package com.github.benyzhous.springboot.sharding.jdbc.masterslave.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.github.benyzhous.springboot.sharding.jdbc.masterslave.model.Order;
import com.github.benyzhous.springboot.sharding.jdbc.masterslave.util.MyMapper;

/**
 * 订单Mapper
 * 
 * @author benyzhous@gmail.com
 */
@Mapper
public interface OrderMapper extends MyMapper<Order>{
	@Delete("delete from t_order")
	public void deleteAll();
}
