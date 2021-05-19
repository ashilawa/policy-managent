package com.policy.management.app.dao.impl;

import com.policy.management.app.dao.PolicyData;
import com.policy.management.app.model.Policy;
import com.policy.management.app.model.PolicyStatus;
import com.policy.management.app.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class PolicyDataImpl implements PolicyData {

    @Autowired
    private PolicyRepository policyRepository;


    @Override
    @Transactional
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    /**
     * @param policyId
     * @return
     */
    @Override
    @Transactional
    public Optional<Policy> getPolicy(Long policyId) {
        return policyRepository.findByPolicyId(policyId);
    }

    /**
     * @param policy
     * @return
     */
    @Override
    @Transactional
    public Policy savePolicy(Policy policy) {
        return policyRepository.save(policy);
    }


    /**
     * @param active
     * @return
     */
    @Override
    @Transactional
    public List<Policy> getAllActivePolicies(PolicyStatus active) {
        return policyRepository.findByStatusOrderByPolicyId(active);
    }
}
