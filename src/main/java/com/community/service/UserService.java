package com.community.service;

/**
 * Created by 舒先亮 on 2019/9/2.
 */

import com.community.mapper.UserMapper;
import com.community.model.User;
import com.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void addOrUpdateUser(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            User dbFindUserByAccountId = users.get(0);

//          参数一：设置一个需要更新的user-->updateUser；参数二：一个new UserExample
            User updateUser = new User();
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            UserExample updateExample = new UserExample();
            updateExample.createCriteria()
                    .andIdEqualTo(dbFindUserByAccountId.getId());
            userMapper.updateByExampleSelective(updateUser, updateExample);
        }
    }
}
