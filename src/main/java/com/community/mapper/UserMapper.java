package com.community.mapper;

import com.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by 舒先亮 on 2019/8/22.
 */
@Mapper
public interface UserMapper {
//    @Select("SELECT * FROM CITY WHERE state = #{state}")
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified}) ")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findUserByToken(String token);
}
