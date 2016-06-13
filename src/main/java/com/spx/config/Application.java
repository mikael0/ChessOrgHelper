package com.spx.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import java.util.TimeZone;


@SpringBootApplication
@EnableAutoConfiguration
@EnableCaching
@EnableSpringConfigured
@ComponentScan("com.spx")
public class Application extends SpringBootServletInitializer {

    public static final String PROPERTIES_PATH = "classpath:myconfig.properties";
    public static final String PROPERTY_MYSQL_SERVICE = "mysql.service";
    public static final String PROPERTY_MONGODB_SERVICE = "mongo.service";
    public static final String PROPERTY_SERVICE_HOST_IP = "service.host.ip";
    public static final String PROPERTY_SERVICE_HOST_URL = "service.host.url";
    public static final String PROPERTY_MYSQL_DRIVER_CLASS = "mysql.driverclass";
    public static final String PROPERTY_MYSQL_URL = "mysql.url";
    public static final String PROPERTY_MONGO_URI = "mongo.uri";
    public static final String PROPERTY_HDFS_ADDRESS = "hdfs.address";
    public static final String PROPERTY_HDFS_PATH_FOLDER_MAIN = "hdfs.path.folder.main";
    public static final String PROPERTY_HDFS_PATH_FOLDER_SYSTEM = "hdfs.path.folder.system";
    public static final String PROPERTY_HDFS_PATH_FOLDER_USERSPACE = "hdfs.path.folder.userspace";
    public static final String PROPERTY_HDFS_USERNAME = "hdfs.username";
    public static final String PROPERTY_HDFS_PATH = "hdfs.path";
    public static final String PROPERTY_HDFS_BROWSER_PATH = "hdfs.browser.path";

    public static final String PROPERTY_HDFS_BROWSER_FILE_ICON = "hdfs.browser.file";
    public static final String PROPERTY_HDFS_BROWSER_FOLDER_ICON = "hdfs.browser.folder";

    public static final String MAX_UPLOAD_SIZE = "upload.size";

    public static final String PROPERTY_EMAIL_ADDRESS = "email.address";
    public static final String PROPERTY_REPORT_FILENAMES = "report.fileNames";

    public static final String PROPERTY_SPARK_APP_NAME = "spark.app.name";
    public static final String PROPERTY_SPARK_APP_MASTER = "spark.app.master";

    public static void main(final String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("storageList");
    }

}

