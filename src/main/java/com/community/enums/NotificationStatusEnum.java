package com.community.enums;

/**
 * Created by 舒先亮 on 2019/9/20.
 */
public enum NotificationStatusEnum {
    READ_STATUS_TRUE(1),
    READ_STATUS_FALSE(0)
    ;


    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
