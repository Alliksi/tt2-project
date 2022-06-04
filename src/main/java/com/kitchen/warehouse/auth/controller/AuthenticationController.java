package com.kitchen.warehouse.auth.controller;

import com.kitchen.warehouse.logger.basic.BasicLogger;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
// @RequestMapping("/")
public class AuthenticationController {

    Logger logger = BasicLogger.getLogger();

    @GetMapping("/")
    public String showIndex() {
        logger.info("Triggered authentication basic mapping");
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        logger.info("Triggered authentication basic mapping");
        return "login";
    }

    @RequestMapping(value={"/register-restaurant"}, method= RequestMethod.GET)
    public String showRegisterRestaurant() {
        logger.info("Triggered register restaurant mapping");
        return "authentication/register_restaurant";
    }

    @RequestMapping(value={"/register-user"}, method= RequestMethod.GET)
    public String showRegisterUser() {
        logger.info("Triggered register user mapping");
        return "authentication/register_user";
    }


}
