package com.storage.logger.database.controller;


import com.storage.logger.database.domain.Log;
import com.storage.logger.database.service.IDatabaseLoggerService;
import com.storage.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class LogsController {
    final IDatabaseLoggerService _databaseLoggerService;

    public LogsController(IDatabaseLoggerService _databaseLoggerService) {
        this._databaseLoggerService = _databaseLoggerService;
    }


    @GetMapping(value = {"/owners/logs"})
    public String showRestaurantDashboard(Principal principal, Model model, User user) {
        if (principal.getName() != null) {
            List<Log> logs = _databaseLoggerService.getAllLogsByUserId(user.getId());
            model.addAttribute("logs", logs);
            return "owners/logs";
        }
        return "redirect:/";
    }
}
