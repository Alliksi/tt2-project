package com.storage.general.errors.controller;

import com.storage.logger.basic.BasicLogger;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class AppErrorController implements ErrorController {

    Logger logger = BasicLogger.getLogger();

    private ErrorAttributes errorAttributes;

    private final static String ERROR_PATH = "/error";

    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorHtml(HttpServletRequest request) {
        logger.error(errorAttributes.getError((WebRequest) request).toString());
        return "general/error";
    }

}
