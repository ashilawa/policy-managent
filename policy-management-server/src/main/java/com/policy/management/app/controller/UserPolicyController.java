package com.policy.management.app.controller;

import com.policy.management.app.exception.ApplicationException;
import com.policy.management.app.model.MessageResponse;
import com.policy.management.app.model.Policy;
import com.policy.management.app.service.PolicyService;
import com.policy.management.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Slf4j
public class UserPolicyController {

    @Autowired
    UserService userServiceImpl;

    @Autowired
    PolicyService policyServiceImpl;

    @GetMapping("/policy/register/{username}/{policyId}")
    public ResponseEntity<MessageResponse> registerPolicy(@PathVariable("policyId") long policyId, @PathVariable("username") String username) {
        try {
          String result =  userServiceImpl.registerPolicy(policyId, username);
            return new ResponseEntity(new MessageResponse(result),HttpStatus.OK);

        } catch (Exception e) {
            throw new ApplicationException("Something went wrong !");
        }

    }

    @GetMapping("/policy/deregister/{username}/{policyId}")
    public ResponseEntity deRegisterPolicy(@PathVariable("policyId") long policyId, @PathVariable("username") String username) {
        try {
            userServiceImpl.deRegisterPolicy(policyId, username);
            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception e) {
            throw new ApplicationException("Something went wrong !");
        }
    }

    @GetMapping("/policy/user")
    public ResponseEntity<Set<Policy>> getUserPolicies(@RequestParam("username") String username) {
        try {
            return new ResponseEntity( userServiceImpl.getUserPolicy(username) ,HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApplicationException("Something went wrong !");

        }
    }
}
