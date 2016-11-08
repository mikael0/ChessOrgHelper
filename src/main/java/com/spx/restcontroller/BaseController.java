package com.spx.restcontroller;


import com.spx.parsers.DefSmetaFERParser;
import org.apache.log4j.Logger;
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

    @Deprecated
    @RequestMapping(value="/parser",  method = RequestMethod.GET)
    public String parser(final Principal principal,
                           @RequestParam(value = "url", required = false) final String url) {
        DefSmetaFERParser parser = new DefSmetaFERParser(url);
        return parser.getFullHTML();
    }



}
