package com.blogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.payloads.LoginForm;
import com.blogApp.security.CustomUserDetailsService;
import com.blogApp.security.JwtService;

@RestController
@RequestMapping("/api")
public class UserAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping("/login")
	public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));
		if(authenticate.isAuthenticated()) {
			return jwtService.generateToken(customUserDetailsService.loadUserByUsername(loginForm.getEmail()));
		}else {
			throw new UsernameNotFoundException("Invalid Credentials");
		}
		
		
	}
}
