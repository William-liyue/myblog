package com.myblog.mapper;

import com.myblog.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Description: user mapper
 * @author li192
 */
@Mapper
public interface UserMapper {

    User getUserByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

    User selectUserByUsername(@Param("username") String username);

    void saveUser(User user);

    boolean updateUser(User user);

    /**
     * 根据id来查询用户
     * @param id
     * @return
     */
    User selectUserById(@Param("id") Integer id);

}
