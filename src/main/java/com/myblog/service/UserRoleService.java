package com.myblog.service;

/**
 * @author li192
 */
public interface UserRoleService {

    /**
     * 为用户添加角色
     * @param userId
     * @param roleId
     */
    void addRoleForUser(String userId, String roleId);

}
