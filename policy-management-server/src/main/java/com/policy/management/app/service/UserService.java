package com.policy.management.app.service;

import com.policy.management.app.model.Policy;
import com.policy.management.app.model.User;

import java.util.Set;

public interface UserService {

    /**
     *
     * @param user
     * @return
     */
    void registerUser(User user);

    /**
     *
     * @param userName
     * @return
     */
    User getUser(String userName);

    /**
     *
     * @param userName
     * @return
     */
    Set<Policy> getUserPolicy(String userName);

    /**
     *
     * @param userName
     * @return
     */
    Boolean existsByUsername(String userName);

    /**
     *
     * @param email
     * @return
     */
    Boolean existsByEmailId(String email);

    /**
     *
     * @param policyId
     * @param username
     * @return
     */
    String registerPolicy(long policyId, String username);

    /**
     *
     * @param policyId
     * @param username
     * @return
     */
    void deRegisterPolicy(long policyId, String username);
}
