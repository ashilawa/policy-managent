package com.policy.management.app.dao;

import com.policy.management.app.model.User;

import java.util.Optional;

public interface UserData {

    /**
     * @param userName
     * @return
     */
     Optional<User> findUser(String userName);

    /**
     *
     * @param user
     */
     void createUser(User user);


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

    /**
     *
     * @param user
     */
    void updateUser(User user);
}
