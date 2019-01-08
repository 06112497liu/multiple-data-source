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
 * 写库数据源
 *
 * @author liuweibo
 * @date 2019/1/2
 */
@Configuration
@MapperScan(basePackages = "com.example.multipledatasource.dao.write", sqlSessionFactoryRef = "writeSqlSessionFactory")
public class WriteDatasourceConfig {

    @Bean("writeDatasource")
    @ConfigurationProperties("spring.datasource.druid.write")
    public DataSource writeDatasource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "writeTransactionManager")
    public DataSourceTransactionManager writeTransactionManager() {
        return new DataSourceTransactionManager(writeDatasource());
    }

    @Bean(name = "writeSqlSessionFactory")
    public SqlSessionFactory writeSqlSessionFactory(@Qualifier("writeDatasource") DataSource writeDatasource)
        throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(writeDatasource);
        return sessionFactory.getObject();
    }
}
