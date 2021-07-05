package com.policy.management.app.dao;

import java.util.List;
import java.util.Optional;

import com.policy.management.app.model.User;
import com.policy.management.app.model.UserStatus;

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
	User updateUser(User user);

	/**
	 * 
	 * @return
	 */
	List<User> getAllUsers();

	/**
	 * 
	 * @param userName
	 * @return
	 */
	UserStatus getUserSatus(String userName);
}
