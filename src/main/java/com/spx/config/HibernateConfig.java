/*
package com.emc.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;




@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    @Bean
    public SessionFactory getSessionFactory(final DataSource dataSource) {
        final LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.scanPackages("com.emc.entity");
        return sessionBuilder.buildSessionFactory();
    }
}
*/
