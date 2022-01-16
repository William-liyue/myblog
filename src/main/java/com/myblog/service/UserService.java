package com.myblog.service;

import com.myblog.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户业务层接口
 * @author li192
 */
public interface UserService {

    /**
     * 根据用户名密码查找对应的用户
     * @param password
     * @param username
     * @return
     */
    User findOne(@Param("username") String username,@Param("password") String password);

    User findByName(String username);
}
