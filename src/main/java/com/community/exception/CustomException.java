package com.community.exception;

/**
 * Created by 舒先亮 on 2019/9/6.
 */
public class CustomException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomException(CustomErrorCodeEnumImp errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
