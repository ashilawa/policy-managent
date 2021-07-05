package com.policy.management.app.dao;

import java.util.List;
import java.util.Optional;

import com.policy.management.app.model.Role;

public interface RoleData {

	/**
	 *
	 * @param roleName
	 * @return
	 */
	Optional<Role> findByRoleName(String roleName);

	/**
	 *
	 * @param
	 * @return
	 */
	List<Role> findAll();
}
