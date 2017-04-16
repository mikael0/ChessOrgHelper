package com.spx.restcontroller;


import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/rest/base")
public class BaseController {
    private static final Logger LOGGER = Logger.getLogger(BaseController.class);

    @RequestMapping(value ="/hello",  method = RequestMethod.GET)
    public String sayHello(final Principal principal,
                                             @RequestParam(value = "onlyDirectories", required = false) final boolean onlyDirectories) {
        return "hello";
    }

}
