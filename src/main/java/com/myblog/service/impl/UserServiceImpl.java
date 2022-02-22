package com.myblog.service.impl;

import com.myblog.exception.TipException;
import com.myblog.mapper.UserMapper;
import com.myblog.model.entity.User;
import com.myblog.service.UserService;
import com.myblog.utils.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author li192
 * @Description: user接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User userLogin(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new TipException("username and password is not empty");
        }
        // 加密
        String pwd = TaleUtils.MD5encode(username + password);
        // 根据用户名和加密后的密码来查询用户是否存在,省去只校验用户名
        User user = userMapper.getUserByUserNameAndPassword(username, pwd);
//        if (null == user){
//
//            log.error("this user is not exist!");
//            throw new TipException("this user is not exist!");
//        }
        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selectUserById(id);
    }
}
