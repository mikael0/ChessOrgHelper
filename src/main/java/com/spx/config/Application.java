package com.spx.config;

import com.spx.email.EmailEntity;
import com.spx.email.EmailSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import java.util.TimeZone;


@SpringBootApplication
//@EnableCaching
@EnableAutoConfiguration
@EnableSpringConfigured
@ComponentScan("com.spx")
public class Application extends SpringBootServletInitializer {

    public static final String PROPERTIES_PATH = "classpath:myconfig.properties";
    public static final String PROPERTY_MONGODB_SERVICE = "mongo.service";
    public static final String PROPERTY_MONGO_URI = "mongo.uri";
    public static final String EMAIL_HOST = "mail.smtp.host";
    public static final String EMAIL_PORT = "mail.smtp.port";
    public static final String EMAIL_ADDRESS = "mail.address";
    public static final String EMAIL_PASSWORD = "mail.password";

    public static final String ORACLE_USER = "oracle.user";
    public static final String ORACLE_PASSWORD = "oracle.password";
    public static final String ORACLE_URL = "oracle.url";

    public static void main(final String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Application.class, args);
    }

   /* @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("storageList");
    }*/

}

