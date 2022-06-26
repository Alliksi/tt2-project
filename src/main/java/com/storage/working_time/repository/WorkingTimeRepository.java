package com.storage.working_time.repository;

import com.storage.working_time.domain.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Integer> {
}