package com.storage.authentication.controller;

import com.storage.authentication.dto.CompanyRegistrationDto;
import com.storage.company.domain.Company;
import com.storage.company.service.ICompanyService;
import com.storage.logger.database.service.IDatabaseLoggerService;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

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
    public String registerNewCompany(@ModelAttribute("company") @Valid CompanyRegistrationDto registrationDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "authentication/register_company";
        }
        try {
            if(_userService.checkIfUserExistsByPersonalCode(registrationDto.getPersonalCode())){
                bindingResult.rejectValue("error.personalCode", "Personal code taken!");
                return "authentication/register_company";
            }

            if(_companyService.checkIfCompanyExistsByRegistrationNumber(registrationDto.getRegistrationNumber())){
                bindingResult.rejectValue("error.registrationNumber", "Registration number taken!");
                return "authentication/register_company";
            }

            if(_userService.checkIfUserExistsByUsername(registrationDto.getUsername())){
                bindingResult.rejectValue("error.username", "Username taken!");
                return "authentication/register_company";
            }

            User registeredUser = _userService.registerNewUser(registrationDto, "ROLE_ADMIN");
            Company registeredCompany = _companyService.registerNewCompany(registrationDto, registeredUser);
            _databaseLoggerService.log("User with ID: " + registeredUser.getId() + " registered a company with ID: " + registeredCompany.getId());
        }
        catch(Exception ex){
            System.err.println(ex);
            model.addAttribute("info", ex.toString());
            return "authentication/register_company";
        }
        model.addAttribute("success", "User created. Please log in.");
        return "authentication/login";
    }

}
