package com.spx.controller;

import com.spx.config.Application;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@SuppressWarnings("HardcodedFileSeparator")
@Controller

@PropertySource(Application.PROPERTIES_PATH)
public class ViewControllers {

    private static final Logger LOGGER = Logger.getLogger(ViewControllers.class);

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/")
    public String startPage() {
        return "login";
    }

    @RequestMapping(value = "/test")
    public String homePage(/*final ModelMap model*/) {
        return "test";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPage() {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }



}