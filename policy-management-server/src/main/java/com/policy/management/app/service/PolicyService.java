package com.policy.management.app.service;

import com.policy.management.app.model.Policy;

import java.util.List;
import java.util.Optional;

public interface PolicyService {

    /**
     *
     * @return
     */
    List<Policy> getAllPolicies();

    /**
     *
     * @param id
     * @return
     */
    Optional<Policy> getPolicy(Long id);

    /**
     *
     * @return
     */
    Policy savePolicy(Policy policy);


    /**
     *
     * @param policy
     * @param policyId
     * @return
     */
    Policy updatePolicy(Policy policy, Long policyId);

    /**
     *
     * @param policyId
     */
    void deletePolicy(Long policyId);

    /**
     *
     * @return
     */
    List<Policy> getAllActivePolicies();


}
