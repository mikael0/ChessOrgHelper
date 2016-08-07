package com.spx.config;

import com.spx.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by Bogdan1 on 07.08.2016.
 */
@Configuration
@PropertySource(Application.PROPERTIES_PATH)
public class EmailConfig {

    @Autowired
    Environment env;

    @Bean
    public EmailSender sender() {
        return new EmailSender(env.getProperty(Application.EMAIL_ADDRESS), env.getProperty(Application.EMAIL_PASSWORD)
                , env.getProperty(Application.EMAIL_HOST), Integer.parseInt(env.getProperty(Application.EMAIL_PORT)));
    }
}
