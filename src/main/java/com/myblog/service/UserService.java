package com.myblog.service;

import com.myblog.model.entity.User;

/**
 * @author itachi
 * @Title: IUserService
 * @Description: user接口
 * @date 2018/5/8下午10:30
 */
public interface IUserService {

    /**
     * Description: 用户的登录接口
     * author: itachi
     * Date: 2018/5/8 下午10:41
     */
    User userLogin(String username, String password);

    /**
     * Description: 保存user
     * author: itachi
     * Date: 2018/5/10 下午10:39
     */
    void saveUser(User user);

    /**
     * Description: 根据用户名来查询用户
     * author: itachi
     * Date: 2018/5/10 下午10:39
     */
    User selectUserByUsername(String username);

    /**
     * Description: 更新用户信息
     * author: itachi
     * Date: 2018/5/10 下午10:40
     */
    boolean updateUser(User user);

    /**
     * Description: 根据id来查询用户
     * author: itachi
     * Date: 2018/5/10 下午10:43
     */
    User selectUserById(Integer id);

}
