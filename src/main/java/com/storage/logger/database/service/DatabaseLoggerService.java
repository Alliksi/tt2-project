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
        Log log = new Log();
        log.setCreated(new Timestamp(System.currentTimeMillis()));
        log.setMessage(message);
        System.out.println(log.getCreated() + " / " + message);
        _logRepository.save(log);
    }

    @Override
    public void log(String message, String status){
        Log log = new Log();
        log.setCreated(new Timestamp(System.currentTimeMillis()));
        log.setMessage(message);
        log.setStatus(status);
        System.out.println(log.getCreated() + " / " + status +  " / " + message);
        _logRepository.save(log);
    }
    @Override
    public void log(String message, String status, String controllerName){
        Log log = new Log();
        log.setCreated(new Timestamp(System.currentTimeMillis()));
        log.setMessage(message);
        log.setStatus(status);
        log.setControllerName(controllerName);
        System.out.println(log.getCreated() + " / " + controllerName + " / " + status +  " / " + message);
        _logRepository.save(log);
    }
    // TODO create more saveLogs where they dont define status or do and dont or do define controllername etc..
}
