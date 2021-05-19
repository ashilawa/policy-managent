package com.policy.management.app.service.Impl;

import com.policy.management.app.dao.PolicyData;
import com.policy.management.app.exception.NoDataFoundException;
import com.policy.management.app.model.Policy;
import com.policy.management.app.model.PolicyStatus;
import com.policy.management.app.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyData policyDataImpl;

    /**
     * @return
     */
    @Override
    public List<Policy> getAllPolicies() {
        return policyDataImpl.getAllPolicies();
    }

    /**
     * @param policyId
     * @return
     */
    @Override
    public Optional<Policy> getPolicy(Long policyId) {
        return policyDataImpl.getPolicy(policyId);
    }

    /**
     * @param policy
     * @return
     */
    @Override
    public Policy savePolicy(Policy policy) {
        policy.setStatus(PolicyStatus.ACTIVE);
        return policyDataImpl.savePolicy(policy);
    }

    /**
     * @param policy
     * @param policyId
     * @return
     */
    @Override
    public Policy updatePolicy(Policy policy, Long policyId) {
        Optional<Policy> policyData = policyDataImpl.getPolicy(policyId);

        if (policyData.isPresent()) {
            Policy _policy = policyData.get();
            _policy.setPolicyName(policy.getPolicyName());
            _policy.setPolicyType(policy.getPolicyType());
            _policy.setDescription(policy.getDescription());
            _policy.setPremium(policy.getPremium());
            _policy.setSumAssured(policy.getSumAssured());
            _policy.setYears(policy.getYears());

            return policyDataImpl.savePolicy(_policy);
        } else {
            throw new NoDataFoundException("Policy Not found");
        }

    }

    /**
     * @param policyId
     */
    @Override
    public void deletePolicy(Long policyId) {
        Optional<Policy> policyData = policyDataImpl.getPolicy(policyId);
        if (policyData.isPresent()) {
            Policy _policy = policyData.get();
            _policy.setStatus(PolicyStatus.INACTIVE);

            policyDataImpl.savePolicy(_policy);
        } else {
            throw new NoDataFoundException("Policy Not found");
        }
    }

    /**
     * @return
     */
    @Override
    public List<Policy> getAllActivePolicies() {
        return policyDataImpl.getAllActivePolicies(PolicyStatus.ACTIVE);
    }


}
