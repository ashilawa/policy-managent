package com.policy.management.app.controller;

import com.policy.management.app.exception.ApplicationException;
import com.policy.management.app.exception.NoDataFoundException;
import com.policy.management.app.model.Policy;
import com.policy.management.app.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PolicyController {

    @Autowired
    PolicyService policyServiceImpl;

    @GetMapping("/policy/all")
    public List<Policy> getAllPolicies() {
        return policyServiceImpl.getAllPolicies();
    }

    @GetMapping("/policy/all/active")
    public List<Policy> getAllActivePolicies() {
        return policyServiceImpl.getAllActivePolicies();
    }

    @GetMapping("/policy/get")
    public Policy getPolicy(@RequestParam Long policyId) {
        return policyServiceImpl.getPolicy(policyId).orElseThrow(() -> new NoDataFoundException("Policy not available !!"));
    }


    @PostMapping("/policy/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
        return new ResponseEntity<>(policyServiceImpl.savePolicy(policy), HttpStatus.CREATED);
    }

    @PutMapping("/policy/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Policy> updatePolicy(@Valid @RequestBody Policy policy, @RequestParam Long policyId) {
        return new ResponseEntity<>(policyServiceImpl.updatePolicy(policy, policyId), HttpStatus.OK);
    }

    @DeleteMapping("/policy/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Policy> deletePolicy(@RequestParam Long policyId) {
        try {
            policyServiceImpl.deletePolicy(policyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ApplicationException("Something went wrong !");
        }
    }
}