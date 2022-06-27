package com.storage.general.controller;

import com.storage.logger.database.service.IDatabaseLoggerService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@ControllerAdvice
@Controller
public class AppErrorController implements ErrorController {

    private ErrorAttributes errorAttributes;
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
