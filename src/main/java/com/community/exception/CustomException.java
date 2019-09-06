package com.community.exception;

/**
 * Created by 舒先亮 on 2019/9/6.
 */
public class CustomException extends RuntimeException {
    private String message;

    public CustomException(String message) {
//        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
