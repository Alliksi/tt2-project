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
import java.security.Principal;


@Controller
public class AuthenticationController {

    private final IDatabaseLoggerService logger;
    private final IUserService _userService;
    private final ICompanyService _companyService;

    public AuthenticationController(IDatabaseLoggerService databaseLoggerService,
                                    IUserService userService, ICompanyService companyService){
        logger = databaseLoggerService;
        _userService = userService;
        _companyService = companyService;
    }

    @GetMapping(value={"/login"})
    public String showLoginView() {
        return "authentication/login";
    }

    @GetMapping(value={"/login-error"})
    public String showInvalidLoginView(Model model) {
        model.addAttribute("invalid", true);
        logger.info("Incorrect login attempt");
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
            var errorFound = false;

            if(registrationDto.getPassword().length() < 6){
                model.addAttribute("errorPassword", "Password must be at least 6 characters long!");
                errorFound = true;
            }
            if(registrationDto.getPersonalCode().length() != 11){
                model.addAttribute("errorPersonalCode", "Personal code must be 11 characters long!");
                errorFound = true;
            }
            if(!registrationDto.getPersonalCode().matches("[0-9]+")){
                model.addAttribute("errorPersonalCode", "Personal code must be numeric!");
                errorFound = true;
            }
            if(registrationDto.getRegistrationNumber().length() != 11){
                model.addAttribute("errorRegistrationNumber", "Registration number must be 11 characters long!");
                errorFound = true;
            }
            if(!registrationDto.getRegistrationNumber().matches("[0-9]+")){
                model.addAttribute("errorRegistrationNumber", "Registration number must be numeric!");
                errorFound = true;
            }
            if(registrationDto.getUsername().length() < 6){
                model.addAttribute("errorUsername", "Username must be at least 6 characters long!");
                errorFound = true;
            }

            if(_userService.checkIfUserExistsByPersonalCode(registrationDto.getPersonalCode())){
                model.addAttribute("errorPersonalCode", "Personal code already taken!");
                errorFound = true;
            }

            if(_companyService.checkIfCompanyExistsByRegistrationNumber(registrationDto.getRegistrationNumber())){
                model.addAttribute("errorRegistrationNumber", "Registration number already taken!");
                errorFound = true;
            }

            if(_userService.checkIfUserExistsByUsername(registrationDto.getUsername())){
                model.addAttribute("errorUsername", "Username already taken!");
                errorFound = true;
            }

            if (errorFound) return "authentication/register_company";

            User registeredUser = _userService.registerNewUser(registrationDto, "ROLE_OWNER");

            Company registeredCompany = _companyService.registerNewCompany(registrationDto, registeredUser);
            logger.info("Registered with username: " + registrationDto.getUsername() + "" + " and registration number: " + registrationDto.getRegistrationNumber());
        }
        catch(Exception ex){
            System.err.println(ex);
            model.addAttribute("info", ex.toString());
            logger.error("Error while registering new company: " + ex.toString());
            return "authentication/register_company";
        }
        model.addAttribute("success", "User created. Please log in.");
        return "authentication/login";
    }

}
