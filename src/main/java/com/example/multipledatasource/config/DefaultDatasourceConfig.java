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
@MapperScan(basePackages = "com.example.multipledatasource.dao.def", sqlSessionFactoryRef = "defaultSqlSessionFactory")
public class DefaultDatasourceConfig {


    @Primary
    @Bean("defaultDatasource")
    @ConfigurationProperties("spring.datasource.druid.def")
    public DataSource defaultDatasource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "defaultTransactionManager")
    public DataSourceTransactionManager defaultTransactionManager() {
        return new DataSourceTransactionManager(defaultDatasource());
    }

    @Primary
    @Bean(name = "defaultSqlSessionFactory")
    public SqlSessionFactory defaultSqlSessionFactory(@Qualifier("defaultDatasource") DataSource defaultDatasource)
        throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(defaultDatasource);
        return sessionFactory.getObject();
    }
}
