package com.myblog.mapper;

import com.myblog.model.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * description
 *
 * @author 70KG
 * @date 2018/9/3
 */

@Mapper
public interface UserPermissionMapper {

    List<Permission> findByUserName(String userName);

}
