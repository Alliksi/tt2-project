package com.storage.logger.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseLogger {
    public static Logger getLogger(){
        System.out.println(DatabaseLogger.class.getName());
        return LogManager.getLogger(DatabaseLogger.class.getName());
    }
}
