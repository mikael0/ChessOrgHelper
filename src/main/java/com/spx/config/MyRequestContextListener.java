package com.spx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.annotation.WebListener;


@Configuration
@WebListener
public class MyRequestContextListener extends RequestContextListener {
    @Override
    public void requestInitialized(ServletRequestEvent requestEvent) {
        super.requestInitialized(requestEvent);
        //requestEvent.getServletContext().addListener(this);
    }
}
