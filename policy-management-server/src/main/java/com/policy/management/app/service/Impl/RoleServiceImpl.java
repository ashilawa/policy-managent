package com.policy.management.app.service.Impl;

import com.policy.management.app.dao.RoleData;
import com.policy.management.app.model.Role;
import com.policy.management.app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleData roleDataImpl;

    /**
     * @param roleName
     * @return
     */
    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return roleDataImpl.findByRoleName(roleName);
    }
}
