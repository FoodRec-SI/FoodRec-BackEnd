package com.foodrec.backend.RecipeAPI.exceptions;

public class InvalidRecipeAttributeException extends RuntimeException{
    private String msg = null;
    public InvalidRecipeAttributeException (String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
