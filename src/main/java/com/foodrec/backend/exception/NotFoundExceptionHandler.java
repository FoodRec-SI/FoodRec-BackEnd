package com.foodrec.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundExceptionHandler extends RuntimeException{
    public NotFoundExceptionHandler(String message) {
        super(message);
    }
}
