
package com.github.benyzhous.springboot.sharding.jdbc.masterslave.rule;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule.TableRuleBuilder;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.time.AbstractClock;
import com.github.benyzhous.springboot.sharding.jdbc.masterslave.algorithm.ModuloDatabaseShardingAlgorithm;
import com.github.benyzhous.springboot.sharding.jdbc.masterslave.algorithm.SingleKeyModuloTableShardingAlgorithm;

/** 
 * 分库分表规则配置
 * 
 * @author benyzhous@gmail.com
 */
@Configuration
public class ShardingRule {
	
	/**
	 * tableRule 规则：分表规则定义 <br>
	 * 参考：<a href="http://dangdangdotcom.github.io/sharding-jdbc/post/user_guide/#intro" >sharding-jdbc开发指南</a>
	 */
	@Bean
	public List<TableRuleBuilder> tableRuleBuilderList(){
		
		TableRuleBuilder orderTableRuleBuilder = 
				TableRule.builder("t_order").
				actualTables(Arrays.asList("t_order_0", "t_order_1","t_order_2","t_order_3"));
		
		TableRuleBuilder orderItemTableRuleBuilder = 
				TableRule.builder("t_order_item").
				actualTables(Arrays.asList("t_order_item_0", "t_order_item_1", "t_order_item_2", "t_order_item_3"));
		
		
		return Arrays.asList(orderTableRuleBuilder, orderItemTableRuleBuilder);
	}
	
	/**
	 * 分库算法策略
	 */
	@Bean
	DatabaseShardingStrategy databaseShardingStrategy(){
		return new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm());
	}
	
	/**
	 * 分表算法策略
	 */
	@Bean
	TableShardingStrategy TableShardingStrategy(){
		return new TableShardingStrategy("order_id", new SingleKeyModuloTableShardingAlgorithm());
	}
	
	@Bean
    public CommonSelfIdGenerator commonSelfIdGenerator() {
        CommonSelfIdGenerator.setClock(AbstractClock.systemClock());
        CommonSelfIdGenerator commonSelfIdGenerator = new CommonSelfIdGenerator();
        return commonSelfIdGenerator;
    }
}
 