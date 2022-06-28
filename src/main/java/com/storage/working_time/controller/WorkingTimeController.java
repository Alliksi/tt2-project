package com.storage.working_time.controller;

import com.storage.product.domain.Product;
import com.storage.product.service.IProductService;
import com.storage.working_time.service.IWorkingTimeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WorkingTimeController {

    private final IWorkingTimeService _workingTimeService;

    public WorkingTimeController(IWorkingTimeService workingTimeService) {
        this._workingTimeService = workingTimeService;
    }


}