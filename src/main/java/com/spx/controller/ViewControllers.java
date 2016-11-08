package com.spx.controller;

import com.spx.config.Application;

import com.spx.dao.UserDao;
import com.spx.entity.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;


@SuppressWarnings("HardcodedFileSeparator")
@Controller

@PropertySource(Application.PROPERTIES_PATH)
public class ViewControllers {

    private static final Logger LOGGER = Logger.getLogger(ViewControllers.class);

    @Autowired
    private Environment environment;

    @Autowired
    UserDao userDao;

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
    public String dashboard(Principal principal, Model model) {
        if (principal instanceof Authentication) {
            model.addAttribute("userName", ((UserDetails)((Authentication) principal).getPrincipal()).getUsername());
            model.addAttribute("userRole", ((UserDetails)((Authentication) principal)
                                            .getPrincipal()).getAuthorities().toArray()[0]);
        }
        return "dashboard";
    }

    @RequestMapping("/external")
    public void externalLogin(Principal principal, HttpServletResponse response) {
        try {
            if (userDao.getUserByLogin(principal.getName(), true).size() == 0)  {
                UserEntity externalUser = new UserEntity();
                UserEntity userEntity = new UserEntity();
                userEntity.setLogin(principal.getName());
                userEntity.setExternal(true);
            }
            response.sendRedirect("/dashboard");
        }
        catch (IOException ex) {
            LOGGER.error("Unable to redirect after succesful external authorization");
        }
    }




}