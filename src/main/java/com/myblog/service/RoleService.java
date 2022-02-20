package com.myblog.service;

import com.myblog.model.entity.Role;

public interface IRoleService {

    /**
     * Description: 根据角色名称查找角色信息
     * Author:70KG
     * Param [roleName]
     * Return Role
     * Date 2018/9/10 13:20
     */
    Role getRoleByRoleName(String roleName);

}
