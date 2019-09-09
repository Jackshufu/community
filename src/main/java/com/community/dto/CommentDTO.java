package com.community.dto;

import lombok.Data;

/**
 * Created by 舒先亮 on 2019/9/9.
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
