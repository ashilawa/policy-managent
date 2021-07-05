package com.policy.management.app.service.Impl;

import com.policy.management.app.dao.PolicyData;
import com.policy.management.app.dao.UserData;
import com.policy.management.app.exception.NoDataFoundException;
import com.policy.management.app.model.Policy;
import com.policy.management.app.model.User;
import com.policy.management.app.model.UserStatus;
import com.policy.management.app.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final String NOT_REGISTERED = "NR";

	private static final String REGISTERED = "R";

	@Autowired
	UserData userDataImpl;

	@Autowired
	private PolicyData policyDataImpl;

	/**
	 * Save user in DB
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public void registerUser(User user) {
		userDataImpl.createUser(user);
	}

	/**
	 * Find user using username
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public User getUser(String userName) {
		return userDataImpl.findUser(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
	}

	/**
	 * @param userName
	 * @return
	 */
	@Override
	public Set<Policy> getUserPolicy(String userName) {
		User user = userDataImpl.findUser(userName)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));

		return user.getPolicies();
	}

	/**
	 * To check username is already present in DB
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public Boolean existsByUsername(String userName) {
		return userDataImpl.existsByUsername(userName);
	}

	/**
	 * To check email ID is already present in DB
	 * 
	 * @param email
	 * @return
	 */
	@Override
	public Boolean existsByEmailId(String email) {
		return userDataImpl.existsByEmail(email);
	}

	/**
	 * Registering the policy for the user
	 * 
	 * @param policyId
	 * @param username
	 * @return
	 */
	@Override
	public String registerPolicy(long policyId, String username) {

		Optional<Policy> policyData = policyDataImpl.getPolicy(policyId);

		Optional<User> userData = userDataImpl.findUser(username);

		if (userData.isPresent()) {
			User user = userData.get();
			Set<Policy> policies = user.getPolicies();
			boolean exist = policies.stream().filter(policy -> policy.getPolicyId() == policyId).findAny().isPresent();
			if (policyData.isPresent() && !exist) {
				Policy policy = policyData.get();
				policies.add(policy);
				userDataImpl.updateUser(user);
			} else {
				logger.info("Policy Not Registered : {} ", username);
				return NOT_REGISTERED;
			}
		} else {
			logger.error("User Not Found with username: {} ", username);
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		logger.info("Policy Registered successfully : {} ", username);
		return REGISTERED;
	}

	/**
	 * De-registering the policy for the user
	 * 
	 * @param policyId
	 * @param username
	 * @return
	 */
	@Override
	public void deRegisterPolicy(long policyId, String username) {
		Optional<User> userData = userDataImpl.findUser(username);
		if (userData.isPresent()) {
			User user = userData.get();
			Set<Policy> policies = user.getPolicies();
			policies.removeIf(policy -> policy.getPolicyId().equals(policyId));
			user.setPolicies(policies);
			userDataImpl.updateUser(user);
			logger.info("User deRegistered successfully : {} ", username);
		} else {
			logger.error("User Not Found with username: {} ", username);
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
	}

	@Override
	public List<User> getUsers() {

		return userDataImpl.getAllUsers();
	}

	@Override
	public User updateUser(User user) {
		Optional<User> userData = userDataImpl.findUser(user.getUsername());

		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setRoles(user.getRoles());
			_user.setStatus(user.getStatus());

			return userDataImpl.updateUser(_user);
		} else {
			logger.error("User Not found for username - {}", user.getUsername());
			throw new NoDataFoundException("User Not found for username : " + user.getUsername());
		}
	}

	@Override
	public UserStatus getUserStatus(String userName) {
		
		return userDataImpl.getUserSatus(userName);
	}

}
