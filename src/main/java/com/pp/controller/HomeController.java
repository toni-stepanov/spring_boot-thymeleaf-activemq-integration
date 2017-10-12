package com.pp.controller;

import com.pp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by astepanov
 */
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String welcome() {
        LOGGER.debug("Getting home page");
        return "home";
    }

    @GetMapping("/home")
    public String toHome() {
        LOGGER.debug("Getting home page");
        return "home";
    }

}
