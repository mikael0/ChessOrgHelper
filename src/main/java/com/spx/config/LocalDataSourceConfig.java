package com.spx.config;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.spx.config.Application;
import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.velocity.tools.config.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.net.UnknownHostException;
import java.sql.SQLException;


@Configuration
@PropertySource(Application.PROPERTIES_PATH)
@Profile("default")
public class LocalDataSourceConfig {

    @Autowired
    Environment env;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String url;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bean
    @Autowired
    DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(env.getProperty(Application.ORACLE_USER));
        dataSource.setPassword(env.getProperty(Application.ORACLE_PASSWORD));
        dataSource.setURL(env.getProperty(Application.ORACLE_URL));
        dataSource.setDriverType("oracle.jdbc.OracleDriver");
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }
}

//@Configuration
//@Profile("default")
//@PropertySource(Application.PROPERTIES_PATH)
//public class LocalDataSourceConfig {
//
//    @Autowired
//    Environment env;
//
///*    @Bean
//    public DataSource testDataSource() {
//            return new EmbeddedDatabaseBuilder()
//                    .setType(EmbeddedDatabaseType.HSQL)
//                    .addScript("classpath:db/schema.sql")
//                    .addScript("classpath:db/test-data.sql")
//                    .build();
//
//    }*/
//
//    @Bean
//    public DataSource oracleDataSource() throws SQLException{
//        OracleDataSource ods = new OracleDataSource();
//        ods.setUser(env.getProperty(Application.ORACLE_USER));
//        ods.setPassword(env.getProperty(Application.ORACLE_PASSWORD));
//        ods.setURL(env.getProperty(Application.ORACLE_URL));
//        ods.setImplicitCachingEnabled(true);
//        ods.setFastConnectionFailoverEnabled(true);
//        return ods;
//    }
//
////    @Bean
////    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
////        final MongoClientURI mongoClientURI = new MongoClientURI(env.getProperty(Application.PROPERTY_MONGO_URI));
////        return new SimpleMongoDbFactory(mongoClientURI);
////    }
////
////    @Bean
////    public MongoTemplate mongoTemplate() throws UnknownHostException {
////        final MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
////        mongoTemplate.setWriteConcern(WriteConcern.SAFE);
////        return mongoTemplate;
////    }
//
//}
