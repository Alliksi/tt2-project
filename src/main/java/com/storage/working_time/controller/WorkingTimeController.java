package com.storage.working_time.controller;

import com.storage.working_time.service.IWorkingTimeService;
import org.springframework.stereotype.Controller;

@Controller
public class WorkingTimeController {

    private final IWorkingTimeService _workingTimeService;

    public WorkingTimeController(IWorkingTimeService workingTimeService) {
        this._workingTimeService = workingTimeService;
    }


}