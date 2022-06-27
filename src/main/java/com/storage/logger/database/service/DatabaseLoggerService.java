package com.storage.logger.database.service;

import com.storage.logger.database.domain.Log;
import com.storage.logger.database.repository.LogRepository;
import com.storage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class DatabaseLoggerService implements IDatabaseLoggerService {

    private final LogRepository _logRepository;
    private final UserService _userService;
    public DatabaseLoggerService(LogRepository logRepository, UserService _userService) {
        _logRepository = logRepository;
        this._userService = _userService;
    }

    @Override
    public void log(String message) {
        log(message, null, "System", 0);
    }
    @Override
    public void log(String message, Principal principal) {
        log(message, principal, null, 0);
    }
    @Override
    public void log(String message, Principal principal, String status) {
        log(message, principal, status, 0);
    }

    @Override
    public void log(String message, Principal principal, String status, int restaurantId) {
        Log log = new Log();
        log.setCreated(new Timestamp(System.currentTimeMillis()));
        log.setMessage(message);
        log.setUserId(_userService.getUserByUsername(principal.getName()).getId());
        if (status != null) log.setStatus(status);
        if (restaurantId == 0) log.setRestaurantId(null);
        else log.setRestaurantId(restaurantId);
        System.out.println(log.getCreated() + " / " + restaurantId + " / " + status + " / " + message);
        _logRepository.save(log);
    }

    @Override
    public void info(String message, Principal principal, int restaurantId) {
        log(message, principal, "Info", restaurantId);
    }
    @Override
    public void info(String message, Principal principal) {
        log(message, principal, "Info");
    }
    @Override
    public void info(String message) {
        log("INFO: " + message);
    }
    @Override
    public void error(String message, Principal principal, int restaurantId) {
        log(message, principal, "Error", restaurantId);
    }
    @Override
    public void error(String message, Principal principal) {
        log(message, principal, "Error");
    }
    @Override
    public void error(String message) {
        log("ERROR: " + message);
    }
    @Override
    public void warn(String message, Principal principal, int restaurantId) {
        log(message, principal, "Warning", restaurantId);
    }
    @Override
    public void warn(String message, Principal principal) {
        log(message, principal, "Warning");
    }
    @Override
    public void warn(String message) {
        log("WARNING: " + message);
    }

    @Override
    public List<Log> getAllLogs() {
        return _logRepository.findAll();
    }
}
