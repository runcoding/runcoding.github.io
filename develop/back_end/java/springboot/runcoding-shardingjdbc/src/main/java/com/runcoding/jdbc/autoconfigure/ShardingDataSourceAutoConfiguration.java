
package com.github.benyzhous.springboot.sharding.jdbc.autoconfigure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule.TableRuleBuilder;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.jdbc.MasterSlaveDataSource;
import com.dangdang.ddframe.rdb.sharding.jdbc.ShardingDataSource;
import com.google.common.base.Splitter;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableConfigurationProperties(ShardingDataSourceProperties.class)
@AutoConfigureAfter(value={DatabaseShardingStrategy.class,TableShardingStrategy.class})
@EnableTransactionManagement
public class ShardingDataSourceAutoConfiguration {
	@Autowired
	private ShardingDataSourceProperties shardingDataSourceProperties;
	
	// 分库分表规则引擎注入
	@Autowired
	List<TableRuleBuilder> tableRuleBuilderList;
	
	@Autowired
	DatabaseShardingStrategy databaseShardingStrategy;
	
	@Autowired
	TableShardingStrategy tableShardingStrategy;

	@Bean
	@Primary
	public DataSource shardingDataSource() throws Exception {
		// masterSlaveDataSource Map
		Map<String,DataSource> dataSourceMap = getMasterSlaveDataSourceMap();
		// dataSourceRule
		DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);
        
        //tableRuleList
        List<TableRule> tableRuleList = new ArrayList<TableRule>(tableRuleBuilderList.size());
        //shardingRule
        //shardingRuleBuilder由业务系统自行构建分库分表规则
        tableRuleBuilderList.forEach(tableRuleBuilder -> {
        		tableRuleList.add(tableRuleBuilder.dataSourceRule(dataSourceRule).build());
        });
        
      //shardingRule
        ShardingRule shardingRule = ShardingRule.builder()
        			.dataSourceRule(dataSourceRule)
				.tableRules(tableRuleList)
				.databaseShardingStrategy(databaseShardingStrategy)
				.tableShardingStrategy(tableShardingStrategy).build();
		
        return new ShardingDataSource(shardingRule);
	}
	
	public Map<String, DataSource> getMasterSlaveDataSourceMap(){
		// dataSourceRule
		Map<String, DataSource> dataSourceMap = new HashMap<>();
		
		List<Map<String,String>> masters = 
				shardingDataSourceProperties.getMasters();

		masters.forEach(map -> {
			
			String url = String.valueOf(map.get("master.url"));
			String username = String.valueOf(map.get("master.username"));
			String password = String.valueOf(map.get("master.password"));
			int initialSize = 2;
			int maxActive = 8;
			int minIdle = 2;
			
			// masterDataSource
			DruidDataSource masterDataSource = new DruidDataSource();
			masterDataSource.setUrl(url);
			masterDataSource.setUsername(username);
			masterDataSource.setPassword(password);
			masterDataSource.setInitialSize(initialSize);
			masterDataSource.setMaxActive(maxActive);
			masterDataSource.setMinIdle(minIdle);
			
			masterDataSource.setTestOnBorrow(true);
	        
			// slaveDataSourceList
			List<DataSource> slaveDataSources = new ArrayList<>();
			List<String> slaves = Splitter.on(",").trimResults().splitToList(map.get("master.slaves"));
			slaves.forEach(slave -> {
				DruidDataSource slaveDataSource = new DruidDataSource();
				String slaveUrl = slave;
				slaveDataSource.setUrl(slaveUrl);
				slaveDataSource.setUsername(username);
				slaveDataSource.setPassword(password);
				slaveDataSource.setInitialSize(initialSize);
				slaveDataSource.setMaxActive(maxActive);
				slaveDataSource.setMinIdle(minIdle);
				
				masterDataSource.setTestOnBorrow(true);
				
				slaveDataSources.add(slaveDataSource);
			});
			
			MasterSlaveDataSource masterSlaveDataSource = 
					new MasterSlaveDataSource(
							String.valueOf(map.get("master.name")),
							masterDataSource, 
							slaveDataSources);
			
			
			dataSourceMap.put(String.valueOf(map.get("master.name")), masterSlaveDataSource);
			
		});
		
		return dataSourceMap;
	}
	
	public static void main(String[] args){
		List<String> result = Splitter.on(",").trimResults().splitToList("jdbc:mysql://localhost:3306/dbtbl_0_master,jdbc:mysql://localhost:3306/dbtbl_0_master");  
		result.forEach(s -> {
			System.err.println(s.toString());
		});
	}
	
}
