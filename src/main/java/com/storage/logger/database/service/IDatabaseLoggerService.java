package com.storage.logger.database.service;

public interface IDatabaseLoggerService {
    void log(String message, String status);
    void log(String message);
    void log(String message, String status, String controllerName);
    void info(String message, String controllerName);
    void warn(String message, String controllerName);
    void error(String message, String controllerName);
    void info(String message);
    void warn(String message);
    void error(String message);
}
