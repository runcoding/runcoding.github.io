
package com.runcoding;

import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.runcoding.model.Order;
import com.runcoding.service.OrderService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
//@SpringApplicationConfiguration(classes = Application.class)// 1.4.0 前版本  
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderServiceTest {
	// id生成器
	@Autowired
    private CommonSelfIdGenerator commonSelfIdGenerator;
	
	@Autowired
	OrderService orderService;
	
	
	@Test
	public void save(){
		orderService.deleteAll();
		// 生成1000条数据，看看分布情况
		for(int i = 0; i < 10; i++){
			Order order = new Order();
			order.setOrderId(commonSelfIdGenerator.generateId().longValue());
			order.setUserId(i);
			order.setStatus("1");
			
			orderService.insertSelective(order);
		}
	}
	
	@Test
	public void saveTransaction(){
		orderService.deleteAll();
		orderService.saveTransaction();
	}
	
	
	@Test
	public void selectAll(){
		List<Order> orderList = orderService.selectAll();
		orderList.forEach(order -> {
			System.err.println(order.toString());
		});
	}
	
	
	//使用Hint强制路由主库示例
	@Test
	public void selectAllHint(){
		List<Order> orderList = orderService.selectAllHint();
		orderList.forEach(order -> {
			System.err.println(order.toString());
		});
	}
}
 