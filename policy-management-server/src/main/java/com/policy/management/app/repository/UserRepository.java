package com.policy.management.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.policy.management.app.model.User;
import com.policy.management.app.model.UserStatus;

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
    
    
    @Query("SELECT u.status FROM User u WHERE u.username =:username")
    public UserStatus getUserStatus(@Param("username") String username);
}
