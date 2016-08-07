package com.spx.controller;

import com.spx.config.Application;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@SuppressWarnings("HardcodedFileSeparator")
@Controller

@PropertySource(Application.PROPERTIES_PATH)
public class ViewControllers {

    private static final Logger LOGGER = Logger.getLogger(ViewControllers.class);

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/")
    public ModelAndView startPage(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        ModelAndView model = new ModelAndView();
        if (csrfToken != null) {
            model.addObject("_csrf",csrfToken);
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/test")
    public String homePage(/*final ModelMap model*/) {
        return "test";
    }

    @RequestMapping(value = "/table")
    public String parserTestPage(/*final ModelMap model*/) {
        return "tablePage";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPage() {
        return "index";
    }

/*    @RequestMapping(value = "/login")
    public String loginPage() {
        return "login";
    }*/

    @RequestMapping(value = "/register")
    public String registerPage() {
        return "register";
    }

    @RequestMapping(value = "/construct")
    public String construct() {
        return "construct";
    }

    @RequestMapping(value = "/dashboard")
    public String dashboard() {
        return "dashboard";
    }




}