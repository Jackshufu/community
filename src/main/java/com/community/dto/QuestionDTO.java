package com.community.dto;

import com.community.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by 舒先亮 on 2019/8/26.
 */
@Data
@Component
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmt_modified;
    private String creator;
    private Long userId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;
}
