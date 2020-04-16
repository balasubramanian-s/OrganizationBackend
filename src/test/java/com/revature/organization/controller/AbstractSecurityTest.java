package com.revature.organization.controller;



import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.test.context.ContextConfiguration;

import com.revature.organization.config.SecurityConfig;


import org.springframework.security.core.Authentication;
@ContextConfiguration(classes =SecurityConfig.class )
abstract public class AbstractSecurityTest {
	
	  @Autowired
	    private AuthenticationManager am;
	  
		@AfterEach
	    public void clear() {
	        SecurityContextHolder.clearContext();
	    }

	    protected void login(String username, String password) {
	    	
	    	
	        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);

        SecurityContextHolder.getContext().setAuthentication(auth);
			
	    }
	}

