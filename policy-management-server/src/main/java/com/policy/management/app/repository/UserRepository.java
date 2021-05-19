package com.policy.management.app.repository;

import com.policy.management.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     *
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     *
     * @param username
     * @return
     */
    Boolean existsByUsername(String username);

    /**
     *
     * @param email
     * @return
     */
    Boolean existsByEmail(String email);
}
