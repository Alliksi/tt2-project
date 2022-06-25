package com.storage.auth.controller;

import com.storage.auth.dto.CompanyRegistrationDto;
import com.storage.logger.basic.BasicLogger;
import com.storage.logger.database.service.IDatabaseLoggerService;
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

    private IDatabaseLoggerService _databaseLoggerService;


    public AuthenticationController(IDatabaseLoggerService databaseLoggerService){
        _databaseLoggerService = databaseLoggerService;
    }

    @GetMapping(value={"/login"})
    public String showLoginView() {
        _databaseLoggerService.log("testing");
        return "authentication/login";
    }

    @GetMapping(value={"/login-error"})
    public String showInvalidLoginView(Model model) {
        model.addAttribute("invalid", true);
        return "authentication/login";
    }

    @RequestMapping(value={"/register-company"}, method= RequestMethod.GET)
    public String showRegisterCompanyView(WebRequest request, Model model) {
        CompanyRegistrationDto company = new CompanyRegistrationDto();
        model.addAttribute("company", company);
        return "authentication/register_company";
    }

    @PostMapping(value={"/register-company"})
    public ModelAndView registerNewCompany(@ModelAttribute("registrationDto") @Valid CompanyRegistrationDto registrationDto,
                                           HttpServletRequest request, Errors errors) {
        _databaseLoggerService.log("User is trying to register a new company");

        return null; // TODO
    }

}
