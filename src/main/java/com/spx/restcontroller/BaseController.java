package com.spx.restcontroller;


import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by timofb on 15-Feb-16.
 */
@RestController
@RequestMapping("/base")
public class BaseController {
    private static final Logger LOGGER = Logger.getLogger(BaseController.class);

    @RequestMapping("/hello")
    public String sayHello(final Principal principal,
                                             @RequestParam(value = "onlyDirectories", required = false) final boolean onlyDirectories,
                                             @RequestParam("path") String path,
                                             @RequestParam(value = "endPath", required = false) final String endPath) {
        return "hello";
    }

}
