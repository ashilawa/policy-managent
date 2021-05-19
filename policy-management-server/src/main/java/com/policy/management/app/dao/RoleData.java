package com.policy.management.app.dao;

import com.policy.management.app.model.Role;

import java.util.Optional;

public interface RoleData {

    /**
     *
     * @param roleName
     * @return
     */
    Optional<Role> findByRoleName(String roleName);
}
