package com.storage.logger.database.service;

import com.storage.logger.database.domain.Log;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface IDatabaseLoggerService {

    void log(String message);
    void log(String message, Principal principal);
    void log(String message, Principal principal, String status);
    void log(String message, Principal principal, String status, int restaurantId);
    void info(String message, Principal principal, int restaurantId);
    void info(String message, Principal principal);
    void info(String message);
    void warn(String message, Principal principal, int restaurantId);
    void warn(String message, Principal principal);
    void warn(String message);
    void error(String message, Principal principal, int restaurantId);
    void error(String message, Principal principal);
    void error(String message);

    List<Log> getAllLogs();
    List<Log> getAllLogsByRestaurantId(int restaurantId);
    List<Log> getAllLogsByUserId(int userId);
    List<Log> getAllLogsByCompanyId(int companyId);

    Page<Log> searchForLogs(String message, int pageNumber);
}
