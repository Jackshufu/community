package com.community.enums;

/**
 * Created by 舒先亮 on 2019/9/9.
 */
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2)
    ;

    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExit(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }
}
