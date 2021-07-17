package com.spartajet.shardingboot.sharding;

import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.time.AbstractClock;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @description
 * @create 2017-02-07 下午10:03
 * @email gxz04220427@163.com
 */
@Configuration
@MapperScan(basePackages = "com.spartajet.shardingboot.mapper", sqlSessionFactoryRef = "sessionFactory")
public class TickSessionFactoryConfig {
    @Bean
    public SqlSessionFactory sessionFactory(DataSource shardingDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(shardingDataSource);
        return sessionFactory.getObject();
    }

    @Bean
    public CommonSelfIdGenerator commonSelfIdGenerator() {
        CommonSelfIdGenerator.setClock(AbstractClock.systemClock());
        CommonSelfIdGenerator commonSelfIdGenerator = new CommonSelfIdGenerator();
        return commonSelfIdGenerator;
    }
}
