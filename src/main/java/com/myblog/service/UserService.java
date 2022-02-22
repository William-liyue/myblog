package com.myblog.service;


import com.myblog.model.entity.User;

/**
 * @author li192
 * @Description: user接口
 */
public interface UserService {

    /**
     * 用户的登录接口
     * @param username
     * @param password
     * @return
     */
    User userLogin(String username, String password);

    /**
     * 保存user
     * @param user
     */
    void saveUser(User user);

    /**
     * 根据用户名来查询用户
     * @param username
     * @return
     */
    User selectUserByUsername(String username);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 根据id来查询用户
     * @param id
     * @return
     */
    User selectUserById(Integer id);

}
