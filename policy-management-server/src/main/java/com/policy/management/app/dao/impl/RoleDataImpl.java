package com.policy.management.app.dao.impl;

import com.policy.management.app.dao.RoleData;
import com.policy.management.app.model.Role;
import com.policy.management.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class RoleDataImpl implements RoleData {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * @param roleName
     * @return
     */
    @Override
    @Transactional
    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
