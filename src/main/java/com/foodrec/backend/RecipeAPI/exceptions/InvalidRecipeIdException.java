package com.foodrec.backend.RecipeAPI.exceptions;

public class InvalidRecipeIdException extends RuntimeException{
    private String msg;
    public InvalidRecipeIdException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
