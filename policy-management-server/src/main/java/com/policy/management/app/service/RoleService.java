package com.policy.management.app.service;

import com.policy.management.app.model.Role;

import java.util.Optional;

public interface RoleService {

    /**
     *
     * @param roleName
     * @return
     */
    Optional<Role> findByRoleName(String roleName);
}
