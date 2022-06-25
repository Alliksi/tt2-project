package com.storage.admin.user;

import com.storage.logger.basic.BasicLogger;
import com.storage.logger.database.DatabaseLogger;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class UserController {

    DatabaseLogger dbLogger = new DatabaseLogger();
    Logger basicLogger = BasicLogger.getLogger();

    @RequestMapping(value={"/admin/user/register-user"}, method= RequestMethod.GET)
    public String showRegisterUserView() {
        basicLogger.info("Triggered register user mapping");
        return "admins/users/register_user"; // TODO
    }

}
