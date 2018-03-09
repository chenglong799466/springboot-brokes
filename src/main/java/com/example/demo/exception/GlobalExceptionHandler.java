package com.example.demo.exception;

import com.example.demo.model.RespCode;
import com.example.demo.model.RespEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public RespEntity handleException(Exception e,HttpServletResponse response){
        logger.error(e.getMessage(),e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        RespEntity respEntity = new RespEntity(RespCode.WARN,e.getClass().getName());
        return respEntity;
    }

}
