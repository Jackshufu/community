package com.community.mapper;

import com.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by 舒先亮 on 2019/8/24.
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,user_id,tag) values (#{title},#{description},#{gmtCreate},#{gmt_modified},#{creator},#{userId},#{tag})")
    void insertQuestion(Question question);

    @Select("select * from question")
    List<Question> queryQuestion();

    @Update("update question set title = #{title},description = #{description},gmt_modified = #{gmt_modified},tag = #{tag} where id = #{id}")
    void updateQuestion(Question question);
}
