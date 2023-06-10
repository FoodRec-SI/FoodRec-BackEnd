package com.foodrec.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataExceptionHandler extends RuntimeException{
    public InvalidDataExceptionHandler(String message){
        super(message);
    }
}
