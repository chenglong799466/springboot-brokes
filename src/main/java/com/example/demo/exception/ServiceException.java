package com.example.demo.exception;

/**
 * @author Chenglong
 * @date 2018.3.12
 *
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String msg){
        super(msg);
    }
}
