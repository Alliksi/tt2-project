package com.storage.logger.database.service;

public interface IDatabaseLoggerService {
    void log(String message, String status);
    void log(String message);
    void log(String message, String status, String controllerName);
}
