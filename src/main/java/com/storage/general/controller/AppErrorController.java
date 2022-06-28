package com.storage.general.controller;

import com.storage.logger.database.service.IDatabaseLoggerService;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@ControllerAdvice
@Controller
public class AppErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;
    private final IDatabaseLoggerService logger;

    private final static String ERROR_PATH = "/error";

    public AppErrorController(ErrorAttributes errorAttributes, IDatabaseLoggerService logger) {
        this.errorAttributes = errorAttributes;
        this.logger = logger;
    }

    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorHtml(HttpServletRequest request, Principal principal) {
        logger.error(request.toString(), principal);
        return "general/error";
    }

}
