package com.revature.organization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.organization.exception.HttpStatusResponse;
import com.revature.organization.model.AuthenticationRequest;
import com.revature.organization.model.AuthenticationResponse;
import com.revature.organization.service.JwtUtil;

@RestController
@CrossOrigin(origins = "*")
public class Authentication {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping({ "/authenticate" })
	public ResponseEntity<HttpStatusResponse> createAuthenticateToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		} catch (BadCredentialsException e) {
			
			return new ResponseEntity<HttpStatusResponse> (
					new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(),"Invalid Username or Password ",null),HttpStatus.BAD_REQUEST);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		String jwt = jwtUtil.generateToken(userDetails);
		return new ResponseEntity<HttpStatusResponse> (new HttpStatusResponse(HttpStatus.OK.value(),"Credentials Verified",new AuthenticationResponse(jwt)),HttpStatus.OK);
		
	}

	
}
