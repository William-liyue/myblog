package com.myblog.service.impl;

import com.myblog.mapper.UserRoleMapper;
import com.myblog.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author li192
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void addRoleForUser(String userId, String roleId) {
        userRoleMapper.addRoleForUser(userId, roleId);
    }

}
