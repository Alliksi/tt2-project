package com.storage.authentication.controller;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.company.domain.Company;
import com.storage.company.service.ICompanyService;
import com.storage.logger.database.service.IDatabaseLoggerService;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
import io.netty.channel.unix.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class AuthenticationController {

    private final IDatabaseLoggerService _databaseLoggerService;
    private final IUserService _userService;
    private final ICompanyService _companyService;

    public AuthenticationController(IDatabaseLoggerService databaseLoggerService,
                                    IUserService userService, ICompanyService companyService){
        _databaseLoggerService = databaseLoggerService;
        _userService = userService;
        _companyService = companyService;
    }

    @GetMapping(value={"/login"})
    public String showLoginView() {
        _databaseLoggerService.info("Logging on");
        return "authentication/login";
    }

    @GetMapping(value={"/login-error"})
    public String showInvalidLoginView(Model model) {
        model.addAttribute("invalid", true);
        _databaseLoggerService.info("Incorrect login attempt");
        return "authentication/login";
    }

    @RequestMapping(value={"/register-company"}, method= RequestMethod.GET)
    public String showRegisterCompanyView(WebRequest request, Model model) {
        CompanyRegistrationDto company = new CompanyRegistrationDto();
        model.addAttribute("company", company);
        return "authentication/register_company";
    }

    @PostMapping(value={"/register-company"})
    public ModelAndView registerNewCompany(@ModelAttribute("company") @Valid CompanyRegistrationDto registrationDto,
                                           HttpServletRequest request, Errors errors, Model model) {
        try {
            User registeredUser = _userService.registerNewUser(registrationDto, "ROLE_ADMIN");
            Company registeredCompany = _companyService.registerNewCompany(registrationDto, registeredUser);
            _databaseLoggerService.log("User with ID: " + registeredUser.getId() + " registered a company with ID: " + registeredCompany.getId());
        }
        catch(Exception ex){
            System.err.println(ex);
            model.addAttribute("info", ex.toString());
            return new ModelAndView("authentication/register_company");
        }
        model.addAttribute("success", "User created. Please log in.");
        return new ModelAndView("authentication/login");
    }

}
