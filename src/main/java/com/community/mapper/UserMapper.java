package com.community.mapper;

import com.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by 舒先亮 on 2019/8/22.
 */
@Mapper
public interface UserMapper {
//    @Select("SELECT * FROM CITY WHERE state = #{state}")
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl} )")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findUserByToken(String token);

    @Select("select * from user where id = #{userId}")
    User findUserById(Integer userId);

    @Select("select * from user where account_id = #{accountId}")
    User findUserByAccountId(String accountId);

    @Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where account_id = #{accountId}")
    void updateUser(User user);
}
