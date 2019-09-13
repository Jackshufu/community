package com.community.exception;

/**
 * Created by 舒先亮 on 2019/9/6.
 */
public enum  CustomErrorCodeEnumImp implements CustomErrorCodeEnum {
    QUESTION_NOT_FOUND(2001,"查询的问题不存在,换一个试试吧"),
    UPDATE_QUESTION_NOT_FOUND(2002,"所更新的问题不存在,换一个试试吧"),
    TARGET_PARAM_NOT_FOUND(2003,"未找到任何问题或评论进行回复"),
    NO_LOGIN(2004,"该用户未登录，请登录"),
    SYSTEM_ERROR(2005,"服务冒烟了，要不然你稍后再试试"),
    TYPE_PARAM_WRONG(2006,"评论类型错误"),
    NO_COMMENT(2007,"暂无评论"),
    QUESTION_NOT_FIND(2008,"没有找到问题"),
    CONTENT_IS_EMPTY(2009,"评论内容为空，请输入内容");



    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomErrorCodeEnumImp(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
