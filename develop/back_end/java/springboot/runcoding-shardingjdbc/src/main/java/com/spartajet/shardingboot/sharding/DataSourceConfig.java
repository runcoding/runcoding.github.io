package com.spartajet.shardingboot.sharding;

ximport com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.DatabaseShardingStrategy;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.TableShardingStrategy;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.spartajet.shardingboot.core.Database;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @description
 * @create 2017-02-07 下午8:57
 * @email gxz04220427@163.com
 */
@Configuration
public class DataSourceConfig {
    @Value("classpath:database.json")
    private Resource databaseFile;

    @Bean
    @Lazy
    public List<Database> databases() throws IOException {
        String databasesString = IOUtils.toString(databaseFile.getInputStream(), Charset.forName("UTF-8"));
        List<Database> databases = new Gson().fromJson(databasesString, new TypeToken<List<Database>>() {
        }.getType());
        return databases;
    }

    @Bean
    public HashMap<String, DataSource> dataSourceMap(List<Database> databases) {
        HashMap<String, DataSource> dataSourceMap = new HashMap<>();
        for (Database database : databases) {
            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
            dataSourceBuilder.url(database.getUrl());
            dataSourceBuilder.driverClassName(database.getDriveClassName());
            dataSourceBuilder.username(database.getUsername());
            dataSourceBuilder.password(database.getPassword());
            DataSource dataSource = dataSourceBuilder.build();
            dataSourceMap.put(database.getName(), dataSource);
        }
        return dataSourceMap;
    }

    @Bean
    @Primary
    public DataSource shardingDataSource(HashMap<String, DataSource> dataSourceMap, DatabaseShardingStrategy databaseShardingStrategy, TableShardingStrategy tableShardingStrategy) {
        DataSourceRule dataSourceRule = new DataSourceRule(dataSourceMap);
        TableRule tableRule = TableRule.builder("tick").actualTables(Arrays.asList("db_sh.tick_a_2017_01", "db_sh.tick_a_2017_02", "db_sh.tick_b_2017_01", "db_sh.tick_b_2017_02", "db_sz.tick_a_2017_01", "db_sz.tick_a_2017_02", "db_sz.tick_b_2017_01", "db_sz.tick_a_2017_02")).dataSourceRule(dataSourceRule).build();
        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule).tableRules(Arrays.asList(tableRule)).databaseShardingStrategy(databaseShardingStrategy).tableShardingStrategy(tableShardingStrategy).build();
        DataSource shardingDataSource = ShardingDataSourceFactory.createDataSource(shardingRule);
        return shardingDataSource;
    }
}
