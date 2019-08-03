package com.lwy.controller;

import com.lwy.entity.Result;
import com.lwy.exception.HealthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class GloabalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GloabalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException",e);
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuffer sb = new StringBuffer();
        for (FieldError fieldError : fieldErrors) {
            sb.append((fieldError.getField() + ":" + fieldError.getDefaultMessage()));
            sb.append(", ");
        }
        if (sb.length()>0){
            sb.setLength(sb.length()-2);
        }
        String message=sb.toString();
        return new Result(false, message);
    }
    @ExceptionHandler(HealthException.class)
    @ResponseBody
    public Result handlerHealthException(HealthException e){
        log.error("HealthException",e);
        return new Result(false, e.getMessage());
    }

}
