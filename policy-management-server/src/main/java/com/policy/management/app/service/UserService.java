package com.policy.management.app.service;

import com.policy.management.app.model.Policy;
import com.policy.management.app.model.User;
import com.policy.management.app.model.UserStatus;

import java.util.List;
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
     * @param userName
     * @return
     */
    UserStatus getUserStatus(String userName);

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

    /**
     * 
     * @return
     */
	List<User> getUsers();
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	User updateUser(User user);
}
