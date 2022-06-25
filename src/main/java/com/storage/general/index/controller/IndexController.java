package com.storage.general.index.controller;

import com.storage.logger.basic.BasicLogger;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    Logger basicLogger = BasicLogger.getLogger();

    @GetMapping(value={"/"})
    public String showIndexView() {
        basicLogger.info("Index view");
        return "general/index";
    }
}
