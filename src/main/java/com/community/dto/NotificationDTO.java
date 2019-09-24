package com.community.dto;

import lombok.Data;

/**
 * Created by 舒先亮 on 2019/9/22.
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long  notifier;
    private String  notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;

}
