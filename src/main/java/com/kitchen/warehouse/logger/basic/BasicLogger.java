package com.kitchen.warehouse.logger.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasicLogger {
    public static Logger getLogger(){
        System.out.println(BasicLogger.class.getName());
        return LogManager.getLogger(BasicLogger.class.getName());
    }
}
