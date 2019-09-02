package com.community.service;

/**
 * Created by 舒先亮 on 2019/9/2.
 */

import com.community.mapper.UserMapper;
import com.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void addOrUpdateUser(User user) {
        User dbFindUserByAccountId = userMapper.findUserByAccountId(user.getAccountId());
        if(dbFindUserByAccountId == null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            dbFindUserByAccountId.setName(user.getName());
            dbFindUserByAccountId.setToken(user.getToken());
            dbFindUserByAccountId.setGmtModified(System.currentTimeMillis());
            dbFindUserByAccountId.setAvatarUrl(user.getAvatarUrl());
            userMapper.updateUser(dbFindUserByAccountId);
        }
    }
}
