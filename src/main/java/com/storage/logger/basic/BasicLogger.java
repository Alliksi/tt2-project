package com.storage.logger.basic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BasicLogger {
    public static Logger getLogger(){
        return LogManager.getLogger(BasicLogger.class.getName());
    }
}
