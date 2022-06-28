package com.storage.logger.database.repository;

import com.storage.logger.database.domain.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Integer> {
    List<Log> findAllByRestaurantId(Integer restaurantId);
    List<Log> findAllByUserId(Integer userId);
    List<Log> findAllByCompanyId(Integer companyId);
    Page<Log> findAllByMessageContainingIgnoreCase(String message, Pageable pageable);
}