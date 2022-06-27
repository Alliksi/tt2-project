package com.storage.logger.database.service;

import com.storage.logger.database.domain.Log;

import java.util.List;

public interface IDatabaseLoggerService {
    void log(String message, String status);
    void log(String message);
    void log(String message, String status, int restaurantId);
    void info(String message, int restaurantId);
    void warn(String message, int restaurantId);
    void error(String message, int restaurantId);
    void info(String message);
    void warn(String message);
    void error(String message);

    List<Log> getAllLogs();
}
