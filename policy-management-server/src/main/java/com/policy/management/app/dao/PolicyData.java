package com.policy.management.app.dao;

import com.policy.management.app.model.Policy;
import com.policy.management.app.model.PolicyStatus;

import java.util.List;
import java.util.Optional;

public interface PolicyData {
    /**
     *
     * @return
     */
    List<Policy> getAllPolicies();

    /**
     *
     * @param policyId
     * @return
     */
    Optional<Policy> getPolicy(Long policyId);


    /**
     *
     * @param policy
     * @return
     */
    Policy savePolicy(Policy policy);

    /**
     *
     * @param active
     * @return
     */
    List<Policy> getAllActivePolicies(PolicyStatus active);
}
