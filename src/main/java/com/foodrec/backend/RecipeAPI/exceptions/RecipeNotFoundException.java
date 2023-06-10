package com.foodrec.backend.RecipeAPI.exceptions;


import org.springframework.web.bind.annotation.ResponseStatus;


public class RecipeNotFoundException extends RuntimeException{
    private String msg;
    public RecipeNotFoundException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
