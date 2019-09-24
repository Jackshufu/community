package com.community.enums;

/**
 * Created by 舒先亮 on 2019/9/20.
 */
public enum NotificationTypeEnum {
    //    在数据库中映射出来的展示
    REPLY_QUESTION(1, "回复了问题"),
    REPLAY_COMMENT(2, "回复了评论");


    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
