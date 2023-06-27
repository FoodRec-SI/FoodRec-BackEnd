package com.foodrec.backend.Utils;

public class CustomResponse<T> {
    private String message;
    private T data;

    public CustomResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
