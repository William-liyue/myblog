package com.myblog.service;

import com.myblog.model.entity.Role;

/**
 * @author li192
 */
public interface RoleService {

    /**
     * 根据角色名称查找角色信息
     * @param roleName
     * @return
     */
    Role getRoleByRoleName(String roleName);

}
