package com.storage.logger.database.controller;


import com.storage.company.service.ICompanyService;
import com.storage.logger.database.domain.Log;
import com.storage.logger.database.service.IDatabaseLoggerService;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class LogsController {

    final IDatabaseLoggerService _databaseLoggerService;
    final ICompanyService _companyService;
    final IUserService _userService;

    public LogsController(IDatabaseLoggerService _databaseLoggerService, ICompanyService companyService, IUserService userService) {
        this._databaseLoggerService = _databaseLoggerService;
        this._companyService = companyService;
        this._userService = userService;
    }


    @GetMapping(value = {"/owners/logs"})
    public String showLogs(Principal principal, Model model, User user) {
        if (principal.getName() != null) {
            List<Log> logs = _databaseLoggerService.getAllLogsByCompanyId(_companyService.getCompanyByUser(_userService.getUserByUsername(principal.getName())).getId());
            model.addAttribute("logs", logs);
            return "owners/logs";
        }
        return "redirect:/";
    }
}
