package com.storage.auth.controller;

import com.storage.logger.basic.BasicLogger;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController {

    Logger logger = BasicLogger.getLogger();

    @GetMapping(value={"/","/login"})
    public String login() {
        logger.info("Login view");
        return "authentication/login";
    }

    @RequestMapping(value={"/login-error"})
    public String loginError(Model model) {
        logger.info("Login error view");
        model.addAttribute("loginError", true);
        return "authentication/login";
    }

    @RequestMapping(value={"/register-restaurant"}, method= RequestMethod.GET)
    public String registerRestaurant() {
        logger.info("Triggered register restaurant mapping");
        return "authentication/register_restaurant";
    }

    @RequestMapping(value={"/register-user"}, method= RequestMethod.GET)
    public String registerUser() {
        logger.info("Triggered register user mapping");
        return "authentication/register_user";
    }


}
