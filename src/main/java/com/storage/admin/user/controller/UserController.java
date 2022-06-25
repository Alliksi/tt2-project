package com.storage.admin.user.controller;

import com.storage.logger.basic.BasicLogger;
import com.storage.logger.database.service.IDatabaseLoggerService;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/admin")
public class UserController {

    IDatabaseLoggerService _databaseLoggerService;
    Logger _basicLogger = BasicLogger.getLogger();

    public UserController(IDatabaseLoggerService databaseLoggerService){
        _databaseLoggerService = databaseLoggerService;
    }

    @RequestMapping(value={"/register-user"}, method= RequestMethod.GET)
    public String showRegisterUserView() {
        _basicLogger.info("Triggered register user mapping");
        return "admins/users/register_user"; // TODO
    }

}
