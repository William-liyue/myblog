package com.myblog.mapper;

import com.myblog.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author li192
 */
@Mapper
public interface UserRoleMapper {

    List<Role> findByUserName(String userName);

    void addRoleForUser(@Param("userId") String userId, @Param("roleId") String roleId);
}
