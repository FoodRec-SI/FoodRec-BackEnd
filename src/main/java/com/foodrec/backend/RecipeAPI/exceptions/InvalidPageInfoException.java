package com.foodrec.backend.RecipeAPI.exceptions;

public class InvalidPageInfoException extends RuntimeException {
    private String msg;
    public InvalidPageInfoException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
