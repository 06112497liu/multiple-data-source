package com.example.multipledatasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 第二个数据源
 *
 * @author liuweibo
 * @date 2019/1/2
 */
@Configuration
@MapperScan(basePackages = "com.example.multipledatasource.dao.second", sqlSessionFactoryRef = "secondSqlSessionFactory")
public class SecondDatasourceConfig {

    @Bean("secondDatasource")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource secondDatasource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager secondTransactionManager() {
        return new DataSourceTransactionManager(secondDatasource());
    }

    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDatasource") DataSource secondDatasource)
        throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondDatasource);
        return sessionFactory.getObject();
    }
}
