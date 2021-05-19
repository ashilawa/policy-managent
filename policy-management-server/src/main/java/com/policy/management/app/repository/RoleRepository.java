package com.policy.management.app.repository;

import com.policy.management.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     *
     * @param roleName
     * @return
     */
    Optional<Role> findByRoleName(String roleName);
}
