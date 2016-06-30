package com.spx.config;

import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.spx.config.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.common.MongoServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.sql.DataSource;
import java.net.UnknownHostException;



@Configuration
@Profile("cloud")
@PropertySource(Application.PROPERTIES_PATH)
public class CloudDataSourceConfig {

    @Autowired
    Environment env;

    @Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
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


//    @Bean
//    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
//        final MongoServiceInfo mongoServiceInfo =
//                (MongoServiceInfo) cloud().getServiceInfo(env.getProperty(Application.PROPERTY_MONGODB_SERVICE));
//        final MongoClientURI mongoClientURI = new MongoClientURI(mongoServiceInfo.getUri());
//        return new SimpleMongoDbFactory(mongoClientURI);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws UnknownHostException {
//        return new MongoTemplate(mongoDbFactory());
//    }
}




