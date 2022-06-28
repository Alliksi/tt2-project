package com.storage.working_time.service;

import com.storage.working_time.domain.WorkingTime;

import java.util.List;

public interface IWorkingTimeService {
    List<WorkingTime> getAll();
}
