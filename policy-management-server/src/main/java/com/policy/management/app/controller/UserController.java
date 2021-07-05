/**
 * 
 */
package com.policy.management.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policy.management.app.exception.ApplicationException;
import com.policy.management.app.model.Role;
import com.policy.management.app.model.User;
import com.policy.management.app.service.RoleService;
import com.policy.management.app.service.UserService;

/**
 * @author aramchan
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	
	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userServiceImpl;
	
	@Autowired
	RoleService roleServiceImpl;
	
	
	@GetMapping("/user/all")
	@PreAuthorize("hasRole('ADMIN') || hasRole('SUPERADMIN')")
	public ResponseEntity<List<User>> getUsers() {
		try {
			return new ResponseEntity<List<User>>(userServiceImpl.getUsers(), HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error occured while getting all Users : {} {} ", e, e.getMessage());

			throw new ApplicationException("Something went wrong ! User retrival failed  " );

		}
	}
	
	@GetMapping("/role/all")
	public ResponseEntity<List<Role>> getRoles() {
		try {
			return new ResponseEntity<List<Role>>(roleServiceImpl.getAllroles(), HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error occured while getting all Roles : {} {} ", e, e.getMessage());

			throw new ApplicationException("Something went wrong ! Role retrival failed  " );

		}
	}

	@PutMapping("/user/update")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
		try {
			return new ResponseEntity<User>(userServiceImpl.updateUser(user), HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error occured while updating user data for username {}  : {} {} ", user.getUsername(),e, e.getMessage());

			throw new ApplicationException("Something went wrong ! user updation failed " );

		}
	}
}