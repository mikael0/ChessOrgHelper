/*
package com.emc.config;

import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.net.UnknownHostException;



@Configuration
@Profile("default")
@PropertySource(Application.PROPERTIES_PATH)
public class LocalDataSourceConfig {

    @Autowired
    Environment env;

    @Bean
    public DriverManagerDataSource mySqlDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(Application.PROPERTY_MYSQL_DRIVER_CLASS));
        dataSource.setUrl(env.getProperty(Application.PROPERTY_MYSQL_URL));
        return dataSource;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        final MongoClientURI mongoClientURI = new MongoClientURI(env.getProperty(Application.PROPERTY_MONGO_URI));
        return new SimpleMongoDbFactory(mongoClientURI);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        final MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        mongoTemplate.setWriteConcern(WriteConcern.SAFE);
        return mongoTemplate;
    }
}*/