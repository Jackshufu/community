package com.community.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by 舒先亮 on 2019/9/18.
 */
@Data
public class tagDTO {
    private String CategoryName;
    List<String> tags;
}
