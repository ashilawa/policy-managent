package com.policy.management.app.service.Impl;

import com.policy.management.app.dao.PolicyData;
import com.policy.management.app.dao.UserData;
import com.policy.management.app.model.Policy;
import com.policy.management.app.model.User;
import com.policy.management.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserData userDataImpl;

    @Autowired
    private PolicyData policyDataImpl;

    /**
     * @param user
     * @return
     */
    @Override
    public void registerUser(User user) {
        userDataImpl.createUser(user);
    }

    /**
     * @param userName
     * @return
     */
    @Override
    public User getUser(String userName) {
        return userDataImpl.findUser(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));
    }

    /**
     * @param userName
     * @return
     */
    @Override
    public Set<Policy> getUserPolicy(String userName) {
        User user = userDataImpl.findUser(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userName));

        return user.getPolicies();
    }

    /**
     * @param userName
     * @return
     */
    @Override
    public Boolean existsByUsername(String userName) {
        return userDataImpl.existsByUsername(userName);
    }

    /**
     * @param email
     * @return
     */
    @Override
    public Boolean existsByEmailId(String email) {
        return userDataImpl.existsByEmail(email);
    }

    /**
     * @param policyId
     * @param username
     * @return
     */
    @Override
    public String registerPolicy(long policyId, String username) {

        Optional<Policy> policyData = policyDataImpl.getPolicy(policyId);

        Optional<User> userData = userDataImpl.findUser(username);

        if (userData.isPresent()) {
            User user = userData.get();
            Set<Policy> policies = user.getPolicies();

            boolean exist = policies.stream().filter(policy -> policy.getPolicyId() == policyId).findAny().isPresent();
            if (policyData.isPresent() && !exist) {
                Policy policy = policyData.get();
                policies.add(policy);
                userDataImpl.updateUser(user);
            } else {
                return "NR";
            }
        }
        return "R";
    }

    /**
     * @param policyId
     * @param username
     * @return
     */
    @Override
    public void deRegisterPolicy(long policyId, String username) {
        Optional<User> userData = userDataImpl.findUser(username);
        if (userData.isPresent()) {
            User user = userData.get();
            Set<Policy> policies = user.getPolicies();
            policies.removeIf(policy -> policy.getPolicyId().equals(policyId));
            user.setPolicies(policies);
            userDataImpl.updateUser(user);
        }
    }
}
