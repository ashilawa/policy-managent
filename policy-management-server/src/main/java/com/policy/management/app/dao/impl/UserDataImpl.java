package com.policy.management.app.dao.impl;

import com.policy.management.app.dao.UserData;
import com.policy.management.app.model.User;
import com.policy.management.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
public class UserDataImpl implements UserData {

    @Autowired
    private UserRepository userRepository;

    /**
     * @param userName
     * @return
     */
    @Override
    @Transactional
    public Optional<User> findUser(String userName) {
        return userRepository.findByUsername(userName);
    }

    /**
     * @param user
     */
    @Override
    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    /**
     * @param username
     * @return
     */
    @Override
    @Transactional
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * @param email
     * @return
     */
    @Override
    @Transactional
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * @param user
     */
    @Override
    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
