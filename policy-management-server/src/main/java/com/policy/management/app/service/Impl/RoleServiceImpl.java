package com.policy.management.app.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.policy.management.app.dao.RoleData;
import com.policy.management.app.model.Role;
import com.policy.management.app.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	
    @Autowired
    private RoleData roleDataImpl;

    /**
     *  Find Role using RoleName
     *  
     * @param roleName
     * @return
     */
    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return roleDataImpl.findByRoleName(roleName);
    }

	@Override
	public List<Role> getAllroles() {
		
		return roleDataImpl.findAll();
	}
    
    
}
