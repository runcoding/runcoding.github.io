package com.runcoding.configurer.data;

import com.github.pagehelper.PageHelper;
import com.runcoding.handler.interceptors.SqlLogInterceptor;
import com.runcoding.handler.type.TypeHandlerRegistrar;
import com.runcoding.handler.type.annotation.ColumnTypeScan;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @module 主数据源配置
 * @author runcoding
 * @date: 2017年10月27日
 */
@Component
@Configuration
@MapperScan(basePackages = "com.runcoding.dao",
            sqlSessionFactoryRef = "mainSqlSessionFactory")
/**@ColumnTypeScan basePackages包下扫描类，支持全局json映射*/
//@ColumnTypeScan(basePackages = "com.runcoding.model")
public class DataSourceConfiguration {


    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSource;

    @Bean(name = "mainDataSource")
    @ConfigurationProperties("spring.datasource")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(dataSource).build();
    }

    @Bean(name = "mainTransactionManager")
    @Primary
    public DataSourceTransactionManager mainTransactionManager(@Qualifier("mainDataSource") DataSource mainDataSource) {
        return new DataSourceTransactionManager(mainDataSource);
    }

    @Bean(name = "mainSqlSessionFactory")
    @Primary
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDataSource") DataSource mainDataSource)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mainDataSource);
        sessionFactory.setPlugins(new Interceptor[]{ new SqlLogInterceptor()});
        SqlSessionFactory session = sessionFactory.getObject();
        /**注册自定义绑定*/
        TypeHandlerRegistrar.typeHandlerRegistry(sessionFactory);
        return session;
    }

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum","true");
        p.setProperty("rowBoundsWithCount","true");
        p.setProperty("reasonable","true");
        pageHelper.setProperties(p);
        return pageHelper;
    }


}


