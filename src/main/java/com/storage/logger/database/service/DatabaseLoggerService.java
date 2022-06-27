package com.storage.logger.database.service;

import com.storage.logger.database.domain.Log;
import com.storage.logger.database.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DatabaseLoggerService implements IDatabaseLoggerService {

    private final LogRepository _logRepository;

    public DatabaseLoggerService(LogRepository logRepository) {
        _logRepository = logRepository;
    }

    @Override
    public void log(String message) {
        log(message, null, 0);
    }

    @Override
    public void log(String message, String status) {
        log(message, status, 0);
    }

    @Override
    public void log(String message, String status, int restaurantId) {
        Log log = new Log();
        log.setCreated(new Timestamp(System.currentTimeMillis()));
        log.setMessage(message);
        if (status != null) log.setStatus(status);
        if (restaurantId == 0) log.setRestaurantId(null);
        else log.setRestaurantId(restaurantId);
        System.out.println(log.getCreated() + " / " + restaurantId + " / " + status + " / " + message);
        _logRepository.save(log);
    }

    @Override
    public void info(String message, int restaurantId) {
        log(message, "Info", restaurantId);
    }

    @Override
    public void warn(String message, int restaurantId) {
        log(message, "Info", restaurantId);
    }

    @Override
    public void error(String message, int restaurantId) {
        log(message, "Info", restaurantId);
    }

    @Override
    public void info(String message) {
        info(message, 0);
    }

    @Override
    public void warn(String message) {
        warn(message, 0);
    }

    @Override
    public void error(String message) {
        error(message, 0);
    }

    @Override
    public List<Log> getAllLogs() {
        return _logRepository.findAll();
    }
}
