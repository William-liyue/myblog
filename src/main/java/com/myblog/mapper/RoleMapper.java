package com.myblog.mapper;

import com.myblog.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author li192
 */
@Mapper
public interface RoleMapper {

    Role getRoleByRoleName(@Param("roleName") String roleName);

}
