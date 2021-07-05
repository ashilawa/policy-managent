package com.policy.management.app.service.Impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.management.app.dao.PolicyData;
import com.policy.management.app.exception.NoDataFoundException;
import com.policy.management.app.model.Policy;
import com.policy.management.app.model.PolicyStatus;
import com.policy.management.app.service.PolicyService;
@Service
public class PolicyServiceImpl implements PolicyService {

	private static Logger logger = LoggerFactory.getLogger(PolicyServiceImpl.class);
	
    @Autowired
    private PolicyData policyDataImpl;

    /**
     *  Get All policies 
     * @return
     */
    @Override
    public List<Policy> getAllPolicies() {
    	
        return policyDataImpl.getAllPolicies();
    }

    /**
     *  Get policy by ID
     * 
     * @param policyId
     * @return
     */
    @Override
    public Optional<Policy> getPolicy(Long policyId) {
        return policyDataImpl.getPolicy(policyId);
    }

    /**
     *  Save new Policy in DB
     *  
     * @param policy
     * @return
     */
    @Override
    public Policy savePolicy(Policy policy) {
        policy.setStatus(PolicyStatus.ACTIVE);
        return policyDataImpl.savePolicy(policy);
    }

    /**
     *  Update policy using policy ID 
     * 
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
        	logger.error("Policy Not found Policy Id - {}", policy.getPolicyId());
            throw new NoDataFoundException("Policy Not found");
        }

    }

    /**
     *  Deactivate policy from the DB ( soft delete policy )
     * 
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
        	logger.error("Policy Not found Policy Id - {}", policyId);
            throw new NoDataFoundException("Policy Not found");
        }
    }

    /**
     *  Get All active Policy 
     * 
     * @return
     */
    @Override
    public List<Policy> getAllActivePolicies() {
        return policyDataImpl.getAllActivePolicies(PolicyStatus.ACTIVE);
    }


}
