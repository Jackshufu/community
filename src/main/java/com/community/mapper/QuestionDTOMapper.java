package com.community.mapper;

import com.community.dto.QuestionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 舒先亮 on 2019/8/26.
 */
@Mapper
public interface QuestionDTOMapper {

    @Select("select * from question")
    List<QuestionDTO> queryQuestionDTO();

    @Select("select * from question where creator = #{creator}")
    List<QuestionDTO> queryMyQuestionDTO(String accountId);
}
