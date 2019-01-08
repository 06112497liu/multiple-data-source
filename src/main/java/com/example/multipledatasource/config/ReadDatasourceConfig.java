package com.example.multipledatasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 默认的数据源
 *
 * @author liuweibo
 * @date 2019/1/2
 */
@Configuration
@MapperScan(basePackages = "com.example.multipledatasource.dao.read", sqlSessionFactoryRef = "readSqlSessionFactory")
public class ReadDatasourceConfig {


    @Primary
    @Bean("readDatasource")
    @ConfigurationProperties("spring.datasource.druid.read")
    public DataSource readDatasource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "readTransactionManager")
    public DataSourceTransactionManager readTransactionManager() {
        return new DataSourceTransactionManager(readDatasource());
    }

    @Primary
    @Bean(name = "readSqlSessionFactory")
    public SqlSessionFactory readSqlSessionFactory(@Qualifier("readDatasource") DataSource readDatasource)
        throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(readDatasource);
        return sessionFactory.getObject();
    }
}
