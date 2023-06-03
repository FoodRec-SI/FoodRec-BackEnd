package com.foodrec.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateExceptionHandler extends RuntimeException {
    public DuplicateExceptionHandler(String message) {
        super(message);
    }

}
