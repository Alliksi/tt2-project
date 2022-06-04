package com.kitchen.warehouse.logger.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserLogger {
    public static Logger getLogger(){
        System.out.println(UserLogger.class.getName());
        return LogManager.getLogger(UserLogger.class.getName());
    }
}
