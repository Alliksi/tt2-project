package com.storage.logger.database.service;

import com.storage.logger.database.domain.Log;
import com.storage.logger.database.repository.LogRepository;
import org.springframework.stereotype.Service;

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
        log.setCreated(LocalDate.from(LocalDateTime.now()));
        log.setMessage(message);
        _logRepository.save(log);
    }

    @Override
    public void log(String message, String status){
        Log log = new Log();
        log.setCreated(LocalDate.from(LocalDateTime.now()));
        log.setMessage(message);
        log.setStatus(status);
        _logRepository.save(log);
    }
    // TODO create more saveLogs where they dont define status or do and dont or do define controllername etc..
}
