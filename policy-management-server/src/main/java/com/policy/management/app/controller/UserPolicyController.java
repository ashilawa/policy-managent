package com.policy.management.app.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.policy.management.app.exception.ApplicationException;
import com.policy.management.app.model.MessageResponse;
import com.policy.management.app.model.Policy;
import com.policy.management.app.service.PolicyService;
import com.policy.management.app.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserPolicyController {

	private static Logger logger = LoggerFactory.getLogger(UserPolicyController.class);

	@Autowired
	UserService userServiceImpl;

	@Autowired
	PolicyService policyServiceImpl;

	@GetMapping("/policy/register/{username}/{policyId}")
	public ResponseEntity<MessageResponse> registerPolicy(@PathVariable("policyId") long policyId,
			@PathVariable("username") String username) {
		try {
			String result = userServiceImpl.registerPolicy(policyId, username);
			return new ResponseEntity<MessageResponse>(new MessageResponse(result), HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Error occured while registering policy {} for user {} : {} {} ", policyId, username, e,
					e.getMessage());

			throw new ApplicationException("Something went wrong ! Policy registering failed..");
		}

	}

	@GetMapping("/policy/deregister/{username}/{policyId}")
	public ResponseEntity<MessageResponse> deRegisterPolicy(@PathVariable("policyId") long policyId,
			@PathVariable("username") String username) {
		try {
			userServiceImpl.deRegisterPolicy(policyId, username);
			return new ResponseEntity<MessageResponse>(new MessageResponse("Policy sucessfully Deregistered "),
					HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error occured while deregistering policy {} for user {} : {} {} ", policyId, username, e,
					e.getMessage());

			throw new ApplicationException("Something went wrong ! Policy deregistering failed ..");
		}
	}

	@GetMapping("/policy/user")
	public ResponseEntity<Set<Policy>> getUserPolicies(@RequestParam String username) {
		try {
			return new ResponseEntity<Set<Policy>>(userServiceImpl.getUserPolicy(username), HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error occured while getting all policies for  {} user : {} {} ", username, e, e.getMessage());

			throw new ApplicationException("Something went wrong ! Policy retrival failed for " + username);

		}
	}
}
