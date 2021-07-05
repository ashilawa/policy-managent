package com.policy.management.app.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.policy.management.app.model.LoginRequest;
import com.policy.management.app.model.LoginResponse;
import com.policy.management.app.security.jwt.JwtUtils;
import com.policy.management.app.security.service.UserDetailsImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	private JwtUtils jwtUtils;

	@Test
	public void shouldReturnOkStatus() throws Exception {
		ResultActions resMockMvc = this.mockMvc.perform(get("/api/auth/test")).andExpect(status().isOk());

		assertNotNull(resMockMvc.andReturn().getResponse().getContentAsString());
		assertEquals("ok", resMockMvc.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void shouldReturnUserWithtoken() throws Exception {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("username");
		loginRequest.setPassword("password");

		UserDetailsImpl userDetails = new UserDetailsImpl(1L, "username", "email", "password",
				Arrays.asList(new SimpleGrantedAuthority("ROLE_" + "USER")));

		Authentication authentication = mock(Authentication.class);

		when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
		when(jwtUtils.generateJwtToken(authentication)).thenReturn("token");

		when(authentication.getPrincipal()).thenReturn(userDetails);

		ResultActions resMockMvc = this.mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(loginRequest))).andExpect(status().isOk());

		LoginResponse loginResponse = new ObjectMapper()
				.readValue(resMockMvc.andReturn().getResponse().getContentAsString(), LoginResponse.class);

		assertNotNull(loginResponse);
		assertEquals("username", loginResponse.getUsername());
		assertEquals(1, loginResponse.getId());
		assertEquals("email", loginResponse.getEmail());
		assertEquals(1, loginResponse.getRoles().size());
	}
	
	
//	@Test
//	public void shouldReturnUserWithtoken() throws Exception {
//
//		LoginRequest loginRequest = new LoginRequest();
//		loginRequest.setUsername("username");
//		loginRequest.setPassword("password");
//
//		UserDetailsImpl userDetails = new UserDetailsImpl(1L, "username", "email", "password",
//				Arrays.asList(new SimpleGrantedAuthority("ROLE_" + "USER")));
//
//		Authentication authentication = mock(Authentication.class);
//
//		when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);
//		when(jwtUtils.generateJwtToken(authentication)).thenReturn("token");
//
//		when(authentication.getPrincipal()).thenReturn(userDetails);
//
//		ResultActions resMockMvc = this.mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON)
//				.content(new ObjectMapper().writeValueAsString(loginRequest))).andExpect(status().isOk());
//
//		LoginResponse loginResponse = new ObjectMapper()
//				.readValue(resMockMvc.andReturn().getResponse().getContentAsString(), LoginResponse.class);
//
//		assertNotNull(loginResponse);
//		assertEquals("username", loginResponse.getUsername());
//		assertEquals(1, loginResponse.getId());
//		assertEquals("email", loginResponse.getEmail());
//		assertEquals(1, loginResponse.getRoles().size());
//	}

}
