package com.storage.logger.database.service;

import com.storage.logger.database.domain.Log;
import com.storage.logger.database.repository.LogRepository;
import com.sun.net.httpserver.HttpContext;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DatabaseLoggerService implements IDatabaseLoggerService {

    private LogRepository _logRepository;

    public DatabaseLoggerService(LogRepository logRepository){
        _logRepository = logRepository;
    }

    @Override
    public void log(String message){
        log(message, null, null);
    }
    @Override
    public void log(String message, String status){
        log(message, status, null);
    }
    @Override
    public void log(String message, String status, String controllerName){
        Log log = new Log();
        log.setCreated(new Timestamp(System.currentTimeMillis()));
        log.setMessage(message);
        if (status != null) log.setStatus(status);
        if (controllerName != null) log.setControllerName(controllerName);
        System.out.println(log.getCreated() + " / " + controllerName + " / " + status +  " / " + message);
        _logRepository.save(log);
    }

    @Override
    public void info(String message, String controllerName){
        log(message, "Info", controllerName);
    }
    @Override
    public void warn(String message, String controllerName){
        log(message, "Info", controllerName);
    }
    @Override
    public void error(String message, String controllerName){
        log(message, "Info", controllerName);
    }
    @Override
    public void info(String message){
        info(message, null);
    }
    @Override
    public void warn(String message){
        warn(message, null);
    }
    @Override
    public void error(String message){
        error(message, null);
    }
    // TODO create more saveLogs where they dont define status or do and dont or do define controllername etc..
}
