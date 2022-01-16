package com.myblog.service.Impl;

import com.myblog.entity.User;
import com.myblog.mapper.UserMapper;
import com.myblog.service.UserService;
import com.myblog.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户业务层接口实现类
 * @author li192
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findOne(String username, String password) {
        password = Md5Utils.md5(password);
        return userMapper.findOne(username,password);
    }

    @Override
    public User findByName(String username) {
        return userMapper.findByName(username);
    }


}
