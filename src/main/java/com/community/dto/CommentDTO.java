package com.community.dto;

import com.community.model.User;
import lombok.Data;

/**
 * Created by 舒先亮 on 2019/9/12.
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private User user;
}
