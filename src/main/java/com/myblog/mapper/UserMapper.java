package com.myblog.mapper;

import com.myblog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.nio.file.attribute.UserPrincipal;

/**
 * 用户持久层接口
 * @author li192
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名密码查找对应的用户
     * @param password
     * @param username
     * @return
     */
    User findOne(@Param("username") String username, @Param("password") String password);

    User findByName(String username);
}
