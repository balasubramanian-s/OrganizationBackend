package com.revature.organization.testContext;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
abstract public class AbstractSecurityTest {
	  @Autowired
	    private AuthenticationManager am;

	    @AfterEach
	    public void clear() {
	        SecurityContextHolder.clearContext();
	    }

	    protected void login(String name, String password) {
	        Authentication auth = new UsernamePasswordAuthenticationToken(name, password);
	        SecurityContextHolder.getContext().setAuthentication(am.authenticate(auth));
	    }
	}

