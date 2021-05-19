package com.policy.management.app.repository;

import com.policy.management.app.model.Policy;
import com.policy.management.app.model.PolicyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    /**
     *
     * @param policyId
     * @return
     */
    Optional<Policy> findByPolicyId(Long policyId);

    /**
     *
     * @param status
     * @return
     */
    List<Policy> findByStatusOrderByPolicyId(PolicyStatus status);

}
