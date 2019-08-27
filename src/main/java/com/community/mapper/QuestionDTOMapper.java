package com.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 舒先亮 on 2019/8/26.
 */
@Mapper
public interface QuestionDTOMapper {

    @Select("select * from question")
    void queryQuestion();
}
