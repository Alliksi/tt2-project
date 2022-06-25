package com.storage.logger.database.repository;

import com.storage.logger.database.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Integer> {
}