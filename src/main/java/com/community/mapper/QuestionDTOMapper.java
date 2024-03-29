package com.community.mapper;

import com.community.dto.QuestionDTO;
import com.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 舒先亮 on 2019/8/26.
 */
@Mapper
public interface QuestionDTOMapper {

    @Select("select * from question order by gmt_create desc")
    List<QuestionDTO> queryQuestionDTO();

    /**
     * 分页查询我的问题列表语句
     * @param userId
     * @return
     */
    @Select("select * from question where user_id = #{userId}")
    List<QuestionDTO> queryMyQuestionDTO(Long userId);


    @Select("select * from question where id = #{id}")
    QuestionDTO queryMyQuestionDTOById(Long id);

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,user_id,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{userId},#{tag})")
    void insertQuestionDTO(Question newQuestion);

}
