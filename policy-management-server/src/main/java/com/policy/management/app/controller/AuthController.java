package com.policy.management.app.controller;


import com.policy.management.app.exception.DuplicateRecordException;
import com.policy.management.app.model.*;
import com.policy.management.app.security.jwt.JwtUtils;
import com.policy.management.app.security.service.UserDetailsImpl;
import com.policy.management.app.service.RoleService;
import com.policy.management.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

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

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority().replace("ROLE_",""))
                .collect(Collectors.toSet());
        log.info("Inside login");
        return ResponseEntity.ok(new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userServiceImpl.existsByUsername(signUpRequest.getUsername())) {
            throw new DuplicateRecordException("Error: Username is already taken!");
        }

        if (userServiceImpl.existsByEmailId(signUpRequest.getEmail())) {
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
        userServiceImpl.registerUser(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
