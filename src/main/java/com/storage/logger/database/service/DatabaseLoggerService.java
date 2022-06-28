package com.storage.logger.database.service;

import com.storage.company.service.ICompanyService;
import com.storage.logger.database.domain.Log;
import com.storage.logger.database.repository.LogRepository;
import com.storage.user.domain.User;
import com.storage.user.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Service
public class DatabaseLoggerService implements IDatabaseLoggerService {

    private final LogRepository _logRepository;
    private final IUserService _userService;
    private final ICompanyService _companyService;

    public DatabaseLoggerService(LogRepository logRepository, IUserService _userService, ICompanyService companyService) {
        this._logRepository = logRepository;
        this._userService = _userService;
        this._companyService = companyService;
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
        if (principal != null && principal.getName() != null) {
            User user = _userService.getUserByUsername(principal.getName());
            log.setUserId(user.getId());
            if(user.getRoles().equals("ROLE_OWNER")){
                log.setCompanyId(_companyService.getCompanyByUser(user).getId());
            }
            else{
                log.setCompanyId(user.getRestaurant().getCompany().getId());
            }

        }
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

    @Override
    public List<Log> getAllLogsByRestaurantId(int restaurantId) {
        return _logRepository.findAllByRestaurantId(restaurantId);
    }

    @Override
    public List<Log> getAllLogsByUserId(int userId) {
        return _logRepository.findAllByUserId(userId);
    }

    @Override
    public List<Log> getAllLogsByCompanyId(int companyId) {
        return _logRepository.findAllByCompanyId(companyId);
    }

    @Override
    public Page<Log> searchForLogs(String message, int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1, 30);
        return _logRepository.findAllByMessageContainingIgnoreCase(message, pageable);
    }
}
