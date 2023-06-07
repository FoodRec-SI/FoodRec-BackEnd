package com.foodrec.backend.RecipeAPI.exceptions;



public class RecipeNotFoundException extends RuntimeException{
    private String msg;
    public RecipeNotFoundException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
