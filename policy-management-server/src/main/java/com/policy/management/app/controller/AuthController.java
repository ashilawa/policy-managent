package com.policy.management.app.controller;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.policy.management.app.exception.ApplicationException;
import com.policy.management.app.exception.DuplicateRecordException;
import com.policy.management.app.model.LoginRequest;
import com.policy.management.app.model.LoginResponse;
import com.policy.management.app.model.MessageResponse;
import com.policy.management.app.model.Role;
import com.policy.management.app.model.SignupRequest;
import com.policy.management.app.model.User;
import com.policy.management.app.model.UserStatus;
import com.policy.management.app.security.jwt.JwtUtils;
import com.policy.management.app.security.service.UserDetailsImpl;
import com.policy.management.app.service.RoleService;
import com.policy.management.app.service.UserService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
	
	private static Logger logger = LoggerFactory.getLogger(AuthController.class);
	

    public static final String USER_ROLE = "USER";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userServiceImpl;

    @Autowired
    RoleService roleServiceImpl;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    @GetMapping("/test")
    public ResponseEntity<String> test() {
    	return ResponseEntity.ok("ok");
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserStatus status = userServiceImpl.getUserStatus(loginRequest.getUsername());
        
        logger.info("User status {} ",status);
        
        if (UserStatus.INACTIVE.equals(status)) {
        	
       	   logger.error("Error: User {} is INACTIVE ! Please contact to SuperAdmin .. ", loginRequest.getUsername());
           throw new ApplicationException("Error: User is INACTIVE ! Please contact to SuperAdmin");
       }

        logger.info("User Login authentication started {} ", loginRequest.getUsername());
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority().replace("ROLE_",""))
                .collect(Collectors.toSet());
        
        logger.info("User Login authentication succesfully done {} ",loginRequest.getUsername());
       
        return ResponseEntity.ok(new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    	
    	logger.info("User signup process started {} ",signUpRequest.getUsername());
    	
        if (userServiceImpl.existsByUsername(signUpRequest.getUsername())) {
        	
        	 logger.error("Error: Username {} is already taken!  ",signUpRequest.getUsername());
            throw new DuplicateRecordException("Error: Username is already taken!");
        }

        if (userServiceImpl.existsByEmailId(signUpRequest.getEmail())) {
        	logger.error("Error: Email {}  is already in use!",signUpRequest.getEmail());
            throw new DuplicateRecordException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
        	
            Role role = roleServiceImpl.findByRoleName(USER_ROLE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(role);
            
        } else {
            strRoles.forEach(roleName -> {
                Role role = roleServiceImpl.findByRoleName(roleName)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                if (role != null) {
                    roles.add(role);
                }
            });
            if (CollectionUtils.isEmpty(roles)) {
                roles.add(new Role(USER_ROLE));
            }
        }
        
        user.setRoles(roles);
        user.setStatus(UserStatus.INACTIVE);
        userServiceImpl.registerUser(user);
        
        logger.info("User {} registered successfully!",signUpRequest.getUsername());
        
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
