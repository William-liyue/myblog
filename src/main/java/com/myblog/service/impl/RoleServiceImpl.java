package com.myblog.service.impl;

import com.myblog.mapper.RoleMapper;
import com.myblog.model.entity.Role;
import com.myblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author li192
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleMapper.getRoleByRoleName(roleName);
    }

}
