package com.storage.working_time.service;

import com.storage.working_time.domain.WorkingTime;
import com.storage.working_time.repository.WorkingTimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingTimeService implements IWorkingTimeService {
    private final WorkingTimeRepository workingTimeRepository;

    public WorkingTimeService(WorkingTimeRepository workingTimeRepository) {
        this.workingTimeRepository = workingTimeRepository;
    }

    @Override
    public List<WorkingTime> getAll() {
        return workingTimeRepository.findAll();
    }
}
