package com.spx.config;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    @Bean
    public SessionFactory getSessionFactory(final DataSource dataSource) {
        final LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.scanPackages("com.spx.entity");
        return sessionBuilder.buildSessionFactory();
    }

/*    @Autowired
    @Bean
    public SessionFactory getSessionFactory(final DataSource dataSource) {
        final LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        sessionBuilder.addProperties(properties);
        sessionBuilder.scanPackages("com.emc.test.entity");
        return sessionBuilder.buildSessionFactory();
    }*/
}

