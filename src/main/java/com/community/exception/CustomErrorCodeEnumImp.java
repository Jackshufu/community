package com.community.exception;

/**
 * Created by 舒先亮 on 2019/9/6.
 */
public enum  CustomErrorCodeEnumImp implements CustomErrorCodeEnum {
    QUESTION_NOT_FOUND("查询的问题不存在,换一个试试吧"),
    UPDATE_QUESTION_NOT_FOUND("所更新的问题不存在,换一个试试吧");


    @Override
    public String getMessage() {
        return message;
    }

    private String message;

    CustomErrorCodeEnumImp(String message) {
        this.message = message;
    }
}
