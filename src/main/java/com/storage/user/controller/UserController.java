package com.storage.user.controller;

import com.storage.logger.database.service.IDatabaseLoggerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/admin")
public class UserController {

    IDatabaseLoggerService _databaseLoggerService;

    public UserController(IDatabaseLoggerService databaseLoggerService){
        _databaseLoggerService = databaseLoggerService;
    }

    @RequestMapping(value={"/register-user"}, method= RequestMethod.GET)
    public String showRegisterUserView() {
        return "admins/users/register_user"; // TODO
    }

}
