package com.storage.auth.controller;

import com.storage.auth.dto.CompanyRegistrationDto;
import com.storage.logger.basic.BasicLogger;
import com.storage.logger.database.DatabaseLogger;
import io.netty.channel.unix.Errors;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class AuthenticationController {

    DatabaseLogger dbLogger = new DatabaseLogger();
    Logger basicLogger = BasicLogger.getLogger();

    @GetMapping(value={"/login"})
    public String showLoginView() {
        basicLogger.info("Login view");
        return "authentication/login";
    }

    @GetMapping(value={"/login-error"})
    public String showInvalidLoginView(Model model) {
        basicLogger.info("Login error view");
        model.addAttribute("invalid", true);
        return "authentication/login";
    }

    @RequestMapping(value={"/register-company"}, method= RequestMethod.GET)
    public String showRegisterCompanyView(WebRequest request, Model model) {
        basicLogger.info("Registering company view");
        CompanyRegistrationDto registrationDto = new CompanyRegistrationDto();
        model.addAttribute("registrationDto", registrationDto);
        return "authentication/register_company";
    }

    @PostMapping(value={"/register-company"})
    public ModelAndView registerNewCompany(@ModelAttribute("registrationDto") @Valid CompanyRegistrationDto registrationDto,
                                           HttpServletRequest request, Errors errors) {
        dbLogger.logToDatabase("User is registering a new company"); // TODO ADD user name, company name, etc.

        return null; // TODO
    }

}
